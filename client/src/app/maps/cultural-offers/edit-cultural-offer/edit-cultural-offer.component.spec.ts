import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { CulturalOfferService } from '../../services/cultural-offer.service';

import { EditCulturalOfferComponent } from './edit-cultural-offer.component';

describe('EditCulturalOfferComponent', () => {
  let component: EditCulturalOfferComponent;
  let fixture: ComponentFixture<EditCulturalOfferComponent>;
  let service: CulturalOfferService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditCulturalOfferComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    let offerServiceMock = {
      update: jasmine.createSpy('update')
        .and.returnValue(of()),
      getOne: jasmine.createSpy('getOne')
        .and.returnValue(of({
          id: 1,
          title: 'offer',
          description: 'desc',
          address: 'adress',
          subTypeName: 'akva',
          offerType: 'park',
          imageUrls: ['img1', 'img2'],
          latitude: 20,
          longitude: 40
        }))
    };

    TestBed.configureTestingModule({
      declarations: [EditCulturalOfferComponent],
      providers: [{ provide: CulturalOfferService, useValue: offerServiceMock }]
    });
    fixture = TestBed.createComponent(EditCulturalOfferComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CulturalOfferService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update offer', () => {
    component.id = 1;
    component.addOffer();
    expect(service.update).toHaveBeenCalledWith(1, {title: 'offer', description: 'desc', imageUrls: []});
  });
});
