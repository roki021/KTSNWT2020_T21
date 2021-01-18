import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { FieldDecorator } from './field-decorator';
import { TableHeader } from './table-header';

@Component({
  selector: 'app-gen-table',
  templateUrl: './gen-table.component.html',
  styleUrls: ['./gen-table.component.sass']
})
export class GenTableComponent<T> implements OnInit {

  @Input() tableData: T[];
  @Input() tableHeader: TableHeader[];
  @Input() rowNum: boolean;
  @Input() fieldDecoration: FieldDecorator;
  data: T[];
  icons: string[];
  page = 1;
  pageSize = 4;
  collectionSize = 0;

  constructor() {}

  ngOnInit(): void {
    this.collectionSize = this.tableData.length;
    this.refreshCountries();
  }

  refreshCountries(): void {
    this.data = this.tableData
      .map((item, i) => ({ id: i + 1, ...item }))
      .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
  }

  generateField(item, headerInfo): string {
    let retVal: string;
    const rowValues: string[] = headerInfo.fieldName.map((elem, i) => item[elem] + '');
    if (headerInfo.headerName === this.fieldDecoration.name) {
      retVal = this.fieldDecoration.decoration.replace(/\{(\d+)\}/g,
      (match, capture) => {
        return rowValues[1 * capture];
      });
    } else {
      retVal = rowValues.join(' ');
    }

    return retVal;
  }
}
