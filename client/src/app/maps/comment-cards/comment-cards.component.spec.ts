import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { CommentInt } from '../model/comment';
import { AddCommentService } from '../services/add-comment.service';

import { CommentCardsComponent } from './comment-cards.component';

describe('CommentCardsComponent', () => {
  let component: CommentCardsComponent;
  let fixture: ComponentFixture<CommentCardsComponent>;
  let addCommentService: AddCommentService;

  beforeEach(async(() => {
    const authServiceMock = {
      getCommentByOffer: jasmine.createSpy('getCommentByOffer')
        .and.returnValue(of([{
          id: 1,
          content:"test",
          commentedOn: new Date(),
          imageUrls: [],
          userId:1,
          culturalOfferId:1
        }]))
    };

    TestBed.configureTestingModule({
      declarations: [ CommentCardsComponent ],
      imports: [
        HttpClientTestingModule
      ],
      providers: [{ provide: AddCommentService, useValue: authServiceMock }]
      })
      .compileComponents();

      fixture = TestBed.createComponent(CommentCardsComponent);
      component = fixture.componentInstance;
      addCommentService = TestBed.inject(AddCommentService);
  }));

  /*beforeEach(() => {
    fixture = TestBed.createComponent(CommentCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });*/

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('listAllComments of offer id', () => {
    component.culturalOffer = {
      id: 1,
      title: '1',
      description: '1',
      address: '',
      subTypeName: '',
      offerType: '',
      imageUrls: [],
      longitude: 0.1,
      latitude: 0.1
    };
    component.ngOnInit();


    expect(addCommentService.getCommentByOffer).toHaveBeenCalled();
    expect(component.comments).toBeDefined();
    expect(component.comments.length).toEqual(1);

  });
});
