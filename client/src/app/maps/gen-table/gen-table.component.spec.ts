import { DebugElement, Pipe, PipeTransform } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Icons } from 'src/app/enums/icons.enum';
import { Subtype } from '../model/subtype';
import { FieldDecorator } from './field-decorator';

import { GenTableComponent } from './gen-table.component';
import { TableHeader } from './table-header';
import { TableOperation } from './table-operation';

@Pipe({ name: 'safeHtml' })
class MockPipe implements PipeTransform {
  transform(value: any): any {
    return value;
  }
}

describe('GenTableComponent', () => {
  let component: GenTableComponent<Subtype>;
  let fixture: ComponentFixture<GenTableComponent<Subtype>>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        GenTableComponent,
        MockPipe
      ]
    });

    const mockData: Subtype[] = [
      {
        name: 'Subtype 1',
        offerNumber: 1,
        offerTypeName: 'Type 1',
        id: 1
      },
      {
        name: 'Subtype 2',
        offerNumber: 2,
        offerTypeName: 'Type 1',
        id: 2
      },
      {
        name: 'Subtype 3',
        offerNumber: 3,
        offerTypeName: 'Type 1',
        id: 3
      }
    ];

    const mockHeader: TableHeader[] = [
      {
        headerName: 'Name',
        fieldName: ['name']
      },
      {
        headerName: 'Offer number',
        fieldName: ['offerNumber']
      },
      {
        headerName: 'Offer type name',
        fieldName: ['offerTypeName']
      }
    ];

    const mockOperations: TableOperation<Subtype>[] = [
      {
        icon: Icons.update,
        operation: (item: Subtype) => { }
      }
    ];

    fixture = TestBed.createComponent<GenTableComponent<Subtype>>(GenTableComponent);
    component = fixture.componentInstance;
    component.tableData = mockData;
    component.tableHeader = mockHeader;
    component.rowNum = true;
    component.operations = mockOperations;
  }));

  it('should create generic table with subtype elements', async(() => {
    component.ngOnInit();

    fixture.whenStable()
      .then(() => {
        expect(component.collectionSize).toBe(3);
        expect(component.rowNum).toBe(true);
        expect(component.tableHeader.length).toBe(3);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('table tr'));
        expect(elements.length).toBe(4);
        const header: DebugElement[] =
          fixture.debugElement.queryAll(By.css('thead tr th'));
        expect(header.length).toBe(5);
      });
  }));

  it('should create generic table with subtype elements and change elements on refresh', async(() => {
    component.ngOnInit();

    fixture.whenStable()
      .then(() => {
        expect(component.collectionSize).toBe(3);
        expect(component.rowNum).toBe(true);
        expect(component.tableHeader.length).toBe(3);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('table tr'));
        expect(elements.length).toBe(4); // header tr plus one tr for each student
        const header: DebugElement[] =
          fixture.debugElement.queryAll(By.css('thead tr th'));
        expect(header.length).toBe(5);

        component.tableData = [
          {
            name: 'Subtype 4',
            offerNumber: 1,
            offerTypeName: 'Type 1',
            id: 4
          },
          {
            name: 'Subtype 4',
            offerNumber: 1,
            offerTypeName: 'Type 1',
            id: 5
          },
        ];

        component.ngDoCheck();

        fixture.whenStable()
          .then(() => {
            expect(component.collectionSize).toBe(2);
            expect(component.rowNum).toBe(true);
            expect(component.tableHeader.length).toBe(3);
            fixture.detectChanges();
            const elementsNew: DebugElement[] =
              fixture.debugElement.queryAll(By.css('table tr'));
            expect(elementsNew.length).toBe(3);
            const headerNew: DebugElement[] =
              fixture.debugElement.queryAll(By.css('thead tr th'));
            expect(headerNew.length).toBe(5);
          });
      });
  }));

  it('should create generic table with decorated fields', async(() => {
    const headerDecoration: FieldDecorator = {
      name: 'Name',
      decoration: `<strong>{0}</strong>`
    };

    component.fieldDecoration = headerDecoration;

    component.ngOnInit();

    fixture.whenStable()
      .then(() => {
        expect(component.collectionSize).toBe(3);
        expect(component.rowNum).toBe(true);
        expect(component.tableHeader.length).toBe(3);
        fixture.detectChanges();
        const elements: DebugElement[] =
          fixture.debugElement.queryAll(By.css('table tr'));
        expect(elements.length).toBe(4);
        const header: DebugElement[] =
          fixture.debugElement.queryAll(By.css('thead tr th'));
        expect(header.length).toBe(5);
        const strongElements: DebugElement[] = fixture.debugElement.queryAll(By.css('strong'));
        expect(strongElements.length).toBe(3);
      });
  }));
});
