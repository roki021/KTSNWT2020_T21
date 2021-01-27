import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CulturalOfferService } from '../../services/cultural-offer.service';

@Component({
  selector: 'app-edit-cultural-offer',
  templateUrl: './edit-cultural-offer.component.html',
  styleUrls: ['./edit-cultural-offer.component.sass']
})
export class EditCulturalOfferComponent implements OnInit {

  @Input('id')
  id: number;

  title: string;

  description: string;

  images: string[];

  base64s: string[];

  @Output()
  sendEvent: EventEmitter<string>;

  constructor(private service: CulturalOfferService) {
    this.sendEvent = new EventEmitter<string>();
  }

  ngOnInit(): void {
    this.service.getOne(this.id).subscribe(res => {
      this.title = res.title;
      this.description = res.description;
      this.images = res.imageUrls;
      this.base64s = [];
    });
  }

  addImage(event){
    let reader = new FileReader();
    reader.onload = (event:any) => {
      console.log(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
      this.images.push(event.target.result);
      this.base64s.push(event.target.result.substring(event.target.result.indexOf("base64,") + 7));
    }
    reader.readAsDataURL(event.target.files[0]);
    for (let img of this.images)
      console.log(img);
  }

  addOffer(){
    this.service.update(this.id, {
      "title": this.title,
      "description": this.description,
      "imageUrls": this.base64s,
    }).subscribe(res => {
      console.log(res.id);
      this.sendEvent.emit("OK");
      alert("Cultural offer updated!");
    }, err => {
      alert("Some changed fields are not valid!");
    })
  }

}
