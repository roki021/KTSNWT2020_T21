import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, ActivatedRoute, convertToParamMap } from '@angular/router';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { OfferNews } from '../../model/offer-news';
import { OfferNewsService } from '../../services/offer-news.service';
import { MockAddNgbModalRef, MockUpdateNgbModalRef } from '../../types/offer-type-list/offer-type-list.component.spec';
import {RouterTestingModule} from '@angular/router/testing';

import { AllOfferNewsComponent } from './all-offer-news.component';
import { AddOfferNewsComponent } from '../add-offer-news/add-offer-news.component';
import { EditOfferNewsComponent } from '../edit-offer-news/edit-offer-news.component';

describe('AllOfferNewsComponent', () => {
  let component: AllOfferNewsComponent;
  let fixture: ComponentFixture<AllOfferNewsComponent>;
  let offerNewsService: OfferNewsService;
  let router: Router;
  let route: ActivatedRoute;
  let modalService: NgbModal;
  let mockAddModalRef: MockAddNgbModalRef = new MockAddNgbModalRef();
  let mockUpdateModalRef: MockUpdateNgbModalRef = new MockUpdateNgbModalRef();

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AllOfferNewsComponent ],
      imports: [
        NgbModule,
        HttpClientTestingModule,
        RouterTestingModule
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {

    TestBed.configureTestingModule({
      declarations: [AllOfferNewsComponent],
      providers: [{ provide: OfferNewsService },
      { provide: ActivatedRoute, 
        useValue: {
          snapshot: {
            paramMap: convertToParamMap({
              offer_id: '2'
            })
          }
        }
      }]
    });

    fixture = TestBed.createComponent(AllOfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    offerNewsService = TestBed.inject(OfferNewsService);
    route = TestBed.inject(ActivatedRoute);
    router = TestBed.inject(Router);
    modalService = TestBed.inject(NgbModal);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch the news list on init', async(() => {
    spyOn(offerNewsService, "getAll").and.returnValue(of([]));
    spyOn(offerNewsService, "getPage").and.returnValue(of([
        {
          id: 1,
          title: 'Ponovno otvaranje parka',
          description: 'Park se otvara opet od 15.01.2021.',
          imageUrls: [],
          date: '08/12/2020 17:11',
          culturalOfferId: 2
        },
        {
          id: 6,
          title: 'Uskoro zatvaranje parka',
          description: 'Park ce biti zatvoren od 30.01.2021.',
          imageUrls: [],
          date: '23/01/2021 21:00',
          culturalOfferId: 2
        }
      ]
    ));
    
    component.ngOnInit();
    expect(offerNewsService.getAll).toHaveBeenCalled();
    expect(offerNewsService.getPage).toHaveBeenCalled();

    expect(component.news.length).toBe(2);
    
  }));

  it('should open pop-up for adding new offer news', () => {
    spyOn(modalService, 'open').and.returnValue(mockAddModalRef as any);
    component.addNew();
    expect(modalService.open).toHaveBeenCalledWith(AddOfferNewsComponent);
  });

  it('should navigate to all offers page', () => {
    spyOn(router, "navigate");
    component.back();
    expect(router.navigate).toHaveBeenCalledWith(['/all_offers']);
  });

  it('should navigate to home page', () => {
    spyOn(router, "navigate");
    component.home();
    expect(router.navigate).toHaveBeenCalledWith(['']);
  });

  it('should navigate to all offer news for given offer page', () => {
    spyOn(router, "navigate");
    component.backNews();
    expect(router.navigate).toHaveBeenCalledWith(['/news/'+component.offerId]);
  });

  it ('should call delete of choosen type', () => {
    spyOn(offerNewsService, "delete").and.returnValue(of())
    component.removeOffer(1);

    expect(offerNewsService.delete).toHaveBeenCalledWith(1);    
  });
  
});
