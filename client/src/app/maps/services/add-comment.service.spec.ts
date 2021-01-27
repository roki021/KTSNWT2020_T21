import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { CommentInt } from '../model/comment';

import { AddCommentService } from './add-comment.service';

describe('AddCommentService', () => {
  let service: AddCommentService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AddCommentService]
    });

    injector = getTestBed();
    service = TestBed.inject(AddCommentService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('add comment', fakeAsync(() => {
    let comment: CommentInt; 
    
    const newType: CommentInt = 
    {
      content: "test",
      commentedOn: new Date(),
      userId:1001,
      culturalOfferId:14,
      imageUrls: []
    }; 
    
    const mockType: CommentInt = 
    {
      id: 1,
      content: "test",
      commentedOn: new Date(),
      userId:1001,
      culturalOfferId:14,
      imageUrls: []
    };      

    service.addCommentCall(newType).subscribe(res => comment = res);

    const req = httpMock.expectOne('http://localhost:8080/c');
    expect(req.request.method).toBe('POST');
    req.flush(mockType);

    tick();
    
    expect(comment).toBeDefined();
    expect(comment.id).toEqual(1);
    expect(comment.content).toEqual("test");
    expect(comment.userId).toEqual(1001);
    expect(comment.culturalOfferId).toEqual(14);

  }));

  it('delete() should query url and delete a comment', () => {
    service.deleteComment(1).subscribe(res => { });
    
    const req = httpMock.expectOne('http://localhost:8080/c/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect({}).toEqual({});
  });

  it('get all comments of cultural offer', fakeAsync(() => {
    let comments: CommentInt[]; 

    const mockComments = [
      {
        id: 1,
        content: "test",
        commentedOn: new Date(),
        userId:1001,
        culturalOfferId:14,
        imageUrls: []
      }
    ];

    service.getCommentByOffer(14).subscribe(res => comments = res);

    const req = httpMock.expectOne('http://localhost:8080/c/culturaloffer/comments/14');
    expect(req.request.method).toBe('GET');
    req.flush(mockComments);

    tick();
    
    expect(comments).toBeDefined();
    expect(comments[0].id).toEqual(1);
    expect(comments[0].content).toEqual('test');
    expect(comments[0].userId).toEqual(1001);
    expect(comments[0].culturalOfferId).toEqual(14);

  }));
});
