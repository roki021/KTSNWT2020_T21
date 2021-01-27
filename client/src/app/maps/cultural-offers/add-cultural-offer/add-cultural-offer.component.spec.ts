import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { CulturalOfferService } from '../../services/cultural-offer.service';
import { SubtypeService } from '../../services/subtype.service';

import { AddCulturalOfferComponent } from './add-cultural-offer.component';

describe('AddCulturalOfferComponent', () => {
  let component: AddCulturalOfferComponent;
  let fixture: ComponentFixture<AddCulturalOfferComponent>;
  let service: CulturalOfferService;
  let subtypeService: SubtypeService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddCulturalOfferComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {

    let offerServiceMock = {
      add: jasmine.createSpy('add')
        .and.returnValue(of()),
      getLocationDetails: jasmine.createSpy('getLocationDetails')
        .and.returnValue(of([{ lon: 20, lat: 41 }])),
    };

    let subtypeServiceMock = {
      getAll: jasmine.createSpy('getAll')
        .and.returnValue(of({
          body: [
            {
              id: 2,
              name: 'nacionalni',
              offerTypeName: 'park',
              offerNumber: 1
            }]
        }))
    }

    TestBed.configureTestingModule({
      declarations: [AddCulturalOfferComponent],
      providers: [{ provide: CulturalOfferService, useValue: offerServiceMock },
      { provide: SubtypeService, useValue: subtypeServiceMock }]
    });
    fixture = TestBed.createComponent(AddCulturalOfferComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CulturalOfferService);
    subtypeService = TestBed.inject(SubtypeService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add offer', () => {
    component.addOffer();
    expect(service.getLocationDetails).toHaveBeenCalled();
    expect(service.add).toHaveBeenCalled();
  });

  it('should get all subtypes', () => {
    component.loadSubtypes();
    expect(subtypeService.getAll).toHaveBeenCalled();
    expect(component.subtypes.length).toBe(1);
  });


});
