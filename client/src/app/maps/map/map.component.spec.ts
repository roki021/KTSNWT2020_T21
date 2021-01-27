import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { of } from 'rxjs';
import { CulturalOfferService } from '../services/cultural-offer.service';

import { MapComponent } from './map.component';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;
  let service: CulturalOfferService;

  beforeEach(async(() => {
    let serviceMock = {
      getPage: jasmine.createSpy('getPage')
        .and.returnValue(of({ body: [{}, {}, {}], headers: { get: (param) => 3 } })),
        search: jasmine.createSpy('search')
        .and.returnValue(of({ body: [{}, {}], headers: { get: (param) => 2 }  })),
    };
    TestBed.configureTestingModule({
      declarations: [ MapComponent ],
      imports: [BrowserAnimationsModule],
      providers: [{ provide: CulturalOfferService, useValue: serviceMock }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(CulturalOfferService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load all offers for given page', () => {
    component.loadAll(1);
    expect(service.getPage).toHaveBeenCalledWith(0, component.pageSize);
    expect(component.culturalOffers.length).toBe(3);
  });

  it('should load all offers found by search for give page', () => {
    component.searchField = 'grade';
    component.searchValue = '2';
    component.search(1);
    
    expect(service.search).toHaveBeenCalledWith('grade', '2', 0, component.pageSize);
    expect(component.searchActive).toBe(true);
    expect(component.culturalOffers.length).toBe(2);
  });

  it('should discard search and load all offers for given page', () => {
    component.discard();
    expect(service.getPage).toHaveBeenCalledWith(0, component.pageSize);
    expect(component.searchActive).toBe(false);
    expect(component.culturalOffers.length).toBe(3);
  });
});
