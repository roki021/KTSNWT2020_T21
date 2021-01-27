import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { OfferNews } from '../model/offer-news';

import { OfferNewsService } from './offer-news.service';

describe('OfferNewsService', () => {
  let service: OfferNewsService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OfferNewsService]
    });

    injector = getTestBed();
    service = TestBed.inject(OfferNewsService);
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getPage() should return some news', fakeAsync(() => {
    let news: OfferNews[];

    const mocknewss: OfferNews[] = [
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
      }];

    service.getPage(2, 0, 6).subscribe(data => {
      news = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/news/by-page/2?page=0&size=6');
    expect(req.request.method).toBe('GET');
    req.flush(mocknewss);

    tick();

    expect(news.length).toEqual(2, 'should contain given amount of news');

    expect(news[0].id).toEqual(1);
    expect(news[0].title).toEqual('Ponovno otvaranje parka');
    expect(news[0].description).toEqual('Park se otvara opet od 15.01.2021.');
    expect(news[0].culturalOfferId).toEqual(2);
    expect(news[0].date).toEqual('08/12/2020 17:11');

    expect(news[1].id).toEqual(6);
    expect(news[1].title).toEqual('Uskoro zatvaranje parka');
    expect(news[1].description).toEqual('Park ce biti zatvoren od 30.01.2021.');
    expect(news[1].culturalOfferId).toEqual(2);
    expect(news[1].date).toEqual('23/01/2021 21:00');

  }));

  it('getOne() should query url and get a news by id', fakeAsync(() => {
    let news: OfferNews;   
    const mocknews: OfferNews = 
    {
      id: 1,
      title: 'Ponovno otvaranje parka',
      description: 'Park se otvara opet od 15.01.2021.',
      imageUrls: [],
      date: '08/12/2020 17:11',
      culturalOfferId: 2          
    };

    service.getOne(1).subscribe(res => news = res);

    const req = httpMock.expectOne('http://localhost:8080/news/1');
    expect(req.request.method).toBe('GET');
    req.flush(mocknews);

    tick();
    
    expect(news).toBeDefined();
    expect(news.id).toEqual(1);
    expect(news.title).toEqual('Ponovno otvaranje parka');
    expect(news.description).toEqual('Park se otvara opet od 15.01.2021.');
    expect(news.date).toEqual('08/12/2020 17:11');
    expect(news.culturalOfferId).toEqual(2);

  }));
  
  it('create() should query url and create a news', fakeAsync(() => {
    let news: OfferNews = {
      title: 'new title',
      description: 'new description',
      imageUrls: [],
      culturalOfferId: 2,
      date: null
    };

    const mocknews: OfferNews = 
    {
      id: 7,
      title: 'new title',
      description: 'new description',
      imageUrls: [],
      culturalOfferId: 2,
      date: null          
    };

    service.add(news).subscribe(res => news = res);
    
    const req = httpMock.expectOne('http://localhost:8080/news');
    expect(req.request.method).toBe('POST');
    req.flush(mocknews);

    tick();
    expect(news).toBeDefined();
    expect(news.id).toEqual(7);
    expect(news.title).toEqual('new title');
    expect(news.description).toEqual('new description');
    expect(news.culturalOfferId).toEqual(2);
  }));

  it('update() should query url and edit a news', fakeAsync(() => {
    let news: OfferNews = {
      id: 1,
      title: 'updated title',
      description: 'updated description',
      imageUrls: [],
      culturalOfferId: 2,
      date: '08/12/2020 17:11'
    };

    const mocknews: OfferNews = 
    {
      id: 1,
      title: 'updated title',
      description: 'updated description',
      imageUrls: [],
      culturalOfferId: 2,
      date: new Date().toDateString()
    };
    
    service.update(1, news).subscribe(res => news = res);
    
    const req = httpMock.expectOne('http://localhost:8080/news/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mocknews);
    
    tick();

    expect(news).toBeDefined();
    expect(news.id).toEqual(1);
    expect(news.title).toEqual('updated title');
    expect(news.description).toEqual('updated description');
    expect(news.culturalOfferId).toEqual(2);
  }));

  it('delete() should query url and delete a news', () => {
    service.delete(1).subscribe(res => { });
    
    const req = httpMock.expectOne('http://localhost:8080/news/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

});
