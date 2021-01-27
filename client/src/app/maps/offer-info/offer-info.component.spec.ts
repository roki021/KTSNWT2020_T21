import { DebugElement } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { Gallery, IvyGalleryModule } from 'angular-gallery';

import { OfferInfoComponent } from './offer-info.component';

describe('OfferInfoComponent', () => {
  let component: OfferInfoComponent;
  let fixture: ComponentFixture<OfferInfoComponent>;
  let galleryService: Gallery;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferInfoComponent ],
      imports: [ IvyGalleryModule ]
    });

    fixture = TestBed.createComponent(OfferInfoComponent);
    component = fixture.componentInstance;
    galleryService = TestBed.inject(Gallery);
    component.selectedOffer = {
      id: 1,
      address: 'Adresa 1',
      description: 'Opis',
      imageUrls: [],
      latitude: 50,
      longitude: 50,
      offerType: 'Tip',
      subTypeName: 'Podtip',
      title: 'Ponuda 1'
    };
  }));

  it('shows cultural offer informations', async(() => {
    component.ngOnInit();

    fixture.whenStable()
      .then(() => {
        fixture.detectChanges(); 
        let elements: DebugElement[] = 
          fixture.debugElement.queryAll(By.css('.row'));
        expect(elements.length).toBe(7);
      });
  }));
});
