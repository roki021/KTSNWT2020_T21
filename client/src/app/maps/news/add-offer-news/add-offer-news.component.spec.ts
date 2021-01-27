import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { OfferNews } from '../../model/offer-news';
import { OfferNewsService } from '../../services/offer-news.service';

import { AddOfferNewsComponent } from './add-offer-news.component';

describe('AddOfferNewsComponent', () => {
  let component: AddOfferNewsComponent;
  let fixture: ComponentFixture<AddOfferNewsComponent>;
  let service: OfferNewsService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddOfferNewsComponent],
      imports: [HttpClientTestingModule]
    })
      .compileComponents();
  }));

  beforeEach(() => {

    TestBed.configureTestingModule({
      declarations: [AddOfferNewsComponent],
      providers: [{ provide: OfferNewsService}]
    });
    fixture = TestBed.createComponent(AddOfferNewsComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(OfferNewsService);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should add news', () => {
    spyOn(service, "add").and.returnValue(of({
      id: 7,
      title: "new title",
      description: "new desc",
      imageUrls: [],
      culturalOfferId: 2
    }));
    component.title = "new title";
    component.description = "new desc";
    component.images = [];
    component.offerId = 2;
    component.addOffer();
    expect(service.add).toHaveBeenCalledWith({
      title: "new title",
      description: "new desc",
      imageUrls: [],
      culturalOfferId: 2
    });
  });

  it('should not add news', () => {
    const error = new Observable<OfferNews>((observer) => {
      observer.error({status: 400});

    });
    spyOn(service, "add").and.returnValue(error);
    spyOn(window, 'alert');
    component.title = "";
    component.description = "new desc";
    component.images = [];
    component.offerId = 2;
    component.addOffer();
    expect(window.alert).toHaveBeenCalledWith('Some fields are empty or invalid!');
  });

});
