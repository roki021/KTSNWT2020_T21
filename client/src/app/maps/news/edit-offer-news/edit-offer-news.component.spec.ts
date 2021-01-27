import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { OfferNewsService } from '../../services/offer-news.service';

import { EditOfferNewsComponent } from './edit-offer-news.component';

describe('EditOfferNewsComponent', () => {
  let component: EditOfferNewsComponent;
  let fixture: ComponentFixture<EditOfferNewsComponent>;
  let service: OfferNewsService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [EditOfferNewsComponent],
      providers: [{ provide: OfferNewsService}],
      imports: [HttpClientTestingModule]
    });
    fixture = TestBed.createComponent(EditOfferNewsComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(OfferNewsService);
    fixture.detectChanges();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditOfferNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update offer', () => {
    spyOn(service, "update").and.returnValue(of({
      id: 1,
      title: "new title",
      description: "new desc",
      imageUrls: [],
      culturalOfferId: 2
    }));
    component.title = "new title";
    component.description = "new desc";
    component.base64s = [];
    component.id = 2;
    component.addOffer();
    expect(service.update).toHaveBeenCalledWith(2, {
      title: "new title",
      description: "new desc",
      imageUrls: [],
    });
  });
});
