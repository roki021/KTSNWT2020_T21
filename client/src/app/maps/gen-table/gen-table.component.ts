import { Component, DoCheck, Input, OnInit } from '@angular/core';
import { FieldDecorator } from './field-decorator';
import { TableHeader } from './table-header';
import { faPlus, faPencilAlt, faTrash, faNewspaper, faEye, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { Icons } from 'src/app/enums/icons.enum';
import { TableOperation } from './table-operation';

@Component({
  selector: 'app-gen-table',
  templateUrl: './gen-table.component.html',
  styleUrls: ['./gen-table.component.sass']
})
export class GenTableComponent<T> implements OnInit, DoCheck {

  @Input() tableData: T[];
  @Input() tableHeader: TableHeader[];
  @Input() rowNum = false;
  @Input() fieldDecoration: FieldDecorator;
  @Input() operations: TableOperation[];
  data: T[];
  page = 1;
  pageSize = 4;
  collectionSize = 0;

  constructor() {}

  ngOnInit(): void {
    this.collectionSize = this.tableData.length;
  }

  ngDoCheck(): void {
    this.collectionSize = this.tableData.length;
    this.refreshItems();
  }

  refreshItems(): void {
    this.data = this.tableData
      .map((item, i) => {
        if (this.rowNum) {
          return { id: i + 1, ...item };
        } else {
          return item;
        }
      });
      // .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
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

  operationIcon(iconName): IconDefinition {
    let iconDef: IconDefinition;

    switch (iconName) {
      case Icons.add:
        iconDef = faPlus;
        break;
      case Icons.news:
        iconDef = faNewspaper;
        break;
      case Icons.preview:
        iconDef = faEye;
        break;
      case Icons.remove:
        iconDef = faTrash;
        break;
      case Icons.update:
        iconDef = faPencilAlt;
        break;
    }

    return iconDef;
  }
}
