import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Subtype } from '../../model/subtype';
import { CulturalOfferService } from '../../services/cultural-offer.service';
import { SubtypeService } from '../../services/subtype.service';

@Component({
  selector: 'app-add-cultural-offer',
  templateUrl: './add-cultural-offer.component.html',
  styleUrls: ['./add-cultural-offer.component.sass']
})
export class AddCulturalOfferComponent implements OnInit {

  title: string;

  description: string;

  address: string;

  subtype: string;

  images: string[];

  displays: string[];

  subtypes: Subtype[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: CulturalOfferService, private subtypeService: SubtypeService) { 
    this.title = "";
    this.description = "";
    this.address = "";
    this.subtype = "";
    this.images = [];
    this.displays = [];
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
    this.loadSubtypes();
  }

  loadSubtypes(){
    this.subtypeService.getAll().subscribe(
      res => {
        this.subtypes = res.body as Subtype[];
      }
    )
  }
  addImage(event){
    var reader = new FileReader();
    console.log("image url: "+event.target.value);
    console.log("files[0]: "+event.target.files[0]);
    reader.onload = (event:any) => {
      this.displays.push(event.target.result);
      this.images.push(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
      console.log(event.target.result);
      console.log(event.target.result.substring(event.target.result.indexOf("base64,")));
    }
    reader.readAsDataURL(event.target.files[0]);
  }

  addOffer(){
    console.log(this.subtype.split(" ", 1)[0].toLowerCase());
    this.service.getLocationDetails(this.address).subscribe(res => {
      if (res.length > 0){
        this.service.add({
          "title": this.title,
          "description": this.description,
          "address": this.address,
          "subTypeName": this.subtype.split(" ", 1)[0].toLowerCase(),
          "imageUrls": this.images,
          "latitude": res[0].lat,
          "longitude": res[0].lon
        }).subscribe(res => {
          console.log("New offer id: "+res.id);
          this.sendEvent.emit("OK");
          alert("New cultural offer added!");
        }, err => {
          alert("Some fields are empty or invalid!");
        });
      }
      else{
        alert("Address given is not valid!");
      }
    });
  }

}
