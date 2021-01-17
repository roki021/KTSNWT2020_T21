import { Component, Input, OnInit } from '@angular/core';
import { Subtype } from '../../model/subtype';

@Component({
  selector: 'app-subtype-table',
  templateUrl: './subtype-table.component.html',
  styleUrls: ['./subtype-table.component.sass']
})
export class SubtypeTableComponent implements OnInit {

  @Input() subtypes:Subtype[];

  constructor() { }

  ngOnInit(): void {
  }

}
