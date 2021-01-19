import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subtype } from '../../model/subtype';
import { SubtypeService } from '../../services/subtype.service';

@Component({
  selector: 'app-subtype-table',
  templateUrl: './subtype-table.component.html',
  styleUrls: ['./subtype-table.component.sass']
})
export class SubtypeTableComponent implements OnInit {

  @Input() subtypes:Subtype[];

  constructor(private subtypes_service: SubtypeService,private _router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  delete(id){
    this.subtypes_service.delete(id).subscribe(res=>{
      if(res.status == 200){
        this._router.routeReuseStrategy.shouldReuseRoute = () => false;
        this._router.onSameUrlNavigation = 'reload';
        this._router.navigate(['./'], { relativeTo: this.route });
      }
    },
    error =>{
      alert("Error")
    });
  }

}
