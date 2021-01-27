import { HttpClient } from '@angular/common/http';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { fakeAsync, getTestBed, TestBed, tick } from '@angular/core/testing';
import { CulturalOffer } from '../model/cultural-offer';

import { CulturalOfferService } from './cultural-offer.service';

describe('CulturalOfferService', () => {
  let service: CulturalOfferService;
  let injector;
  let httpMock: HttpTestingController;
  let httpClient: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CulturalOfferService]
    });

    injector = getTestBed();
    httpClient = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
    service = TestBed.inject(CulturalOfferService);
  });

  afterEach(() => {
    httpMock.verify();
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('getPage() should return some offers', fakeAsync(() => {
    let offer: CulturalOffer[];

    const mockOffer: CulturalOffer[] = [
      {
        id: 1,
        title: 'offer',
        description: 'desc',
        address: 'adress',
        subTypeName: 'akva',
        offerType: 'park',
        imageUrls: ['img1', 'img2'],
        latitude: 20,
        longitude: 40
      },
      {
        id: 2,
        title: 'offer2',
        description: 'desc2',
        address: 'adress2',
        subTypeName: 'akva2',
        offerType: 'park2',
        imageUrls: ['img1', 'img2'],
        latitude: 21,
        longitude: 42
      }];

    service.getPage(0, 2).subscribe(data => {
      offer = data.body;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers/by-page?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockOffer);

    tick();

    expect(offer.length).toEqual(2);
    expect(offer[0].id).toEqual(1);
    expect(offer[0].title).toEqual('offer');
    expect(offer[0].description).toEqual('desc');
    expect(offer[0].address).toEqual('adress');
    expect(offer[0].subTypeName).toEqual('akva');
    expect(offer[0].offerType).toEqual('park');
    expect(offer[0].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[0].latitude).toEqual(20);
    expect(offer[0].longitude).toEqual(40);

    expect(offer[1].id).toEqual(2);
    expect(offer[1].title).toEqual('offer2');
    expect(offer[1].description).toEqual('desc2');
    expect(offer[1].address).toEqual('adress2');
    expect(offer[1].subTypeName).toEqual('akva2');
    expect(offer[1].offerType).toEqual('park2');
    expect(offer[1].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[1].latitude).toEqual(21);
    expect(offer[1].longitude).toEqual(42);

  }));

  it('search() should return some offers found by search parametars', fakeAsync(() => {
    let offer: CulturalOffer[];

    const mockOffer: CulturalOffer[] = [
      {
        id: 1,
        title: 'offer',
        description: 'desc',
        address: 'adress',
        subTypeName: 'akva',
        offerType: 'park',
        imageUrls: ['img1', 'img2'],
        latitude: 20,
        longitude: 40
      },
      {
        id: 2,
        title: 'offer2',
        description: 'desc2',
        address: 'adress2',
        subTypeName: 'akva2',
        offerType: 'park2',
        imageUrls: ['img1', 'img2'],
        latitude: 21,
        longitude: 42
      }];

    service.search('grade', '2', 0, 2).subscribe(data => {
      offer = data.body;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers/search?page=0&size=2');
    expect(req.request.method).toBe('POST');
    req.flush(mockOffer);

    tick();

    expect(offer.length).toEqual(2);
    expect(offer[0].id).toEqual(1);
    expect(offer[0].title).toEqual('offer');
    expect(offer[0].description).toEqual('desc');
    expect(offer[0].address).toEqual('adress');
    expect(offer[0].subTypeName).toEqual('akva');
    expect(offer[0].offerType).toEqual('park');
    expect(offer[0].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[0].latitude).toEqual(20);
    expect(offer[0].longitude).toEqual(40);

    expect(offer[1].id).toEqual(2);
    expect(offer[1].title).toEqual('offer2');
    expect(offer[1].description).toEqual('desc2');
    expect(offer[1].address).toEqual('adress2');
    expect(offer[1].subTypeName).toEqual('akva2');
    expect(offer[1].offerType).toEqual('park2');
    expect(offer[1].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[1].latitude).toEqual(21);
    expect(offer[1].longitude).toEqual(42);

  }));

  it('getPage2() should return some offers', fakeAsync(() => {
    let offer: CulturalOffer[];

    const mockOffer: CulturalOffer[] = [
      {
        id: 1,
        title: 'offer',
        description: 'desc',
        address: 'adress',
        subTypeName: 'akva',
        offerType: 'park',
        imageUrls: ['img1', 'img2'],
        latitude: 20,
        longitude: 40
      },
      {
        id: 2,
        title: 'offer2',
        description: 'desc2',
        address: 'adress2',
        subTypeName: 'akva2',
        offerType: 'park2',
        imageUrls: ['img1', 'img2'],
        latitude: 21,
        longitude: 42
      }];

    service.getPage2(0, 2).subscribe(data => {
      offer = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers/by-page?page=0&size=2');
    expect(req.request.method).toBe('GET');
    req.flush(mockOffer);

    tick();

    expect(offer.length).toEqual(2);
    expect(offer[0].id).toEqual(1);
    expect(offer[0].title).toEqual('offer');
    expect(offer[0].description).toEqual('desc');
    expect(offer[0].address).toEqual('adress');
    expect(offer[0].subTypeName).toEqual('akva');
    expect(offer[0].offerType).toEqual('park');
    expect(offer[0].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[0].latitude).toEqual(20);
    expect(offer[0].longitude).toEqual(40);

    expect(offer[1].id).toEqual(2);
    expect(offer[1].title).toEqual('offer2');
    expect(offer[1].description).toEqual('desc2');
    expect(offer[1].address).toEqual('adress2');
    expect(offer[1].subTypeName).toEqual('akva2');
    expect(offer[1].offerType).toEqual('park2');
    expect(offer[1].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[1].latitude).toEqual(21);
    expect(offer[1].longitude).toEqual(42);

  }));

  it('getAll() should return all offers', fakeAsync(() => {
    let offer: CulturalOffer[];

    const mockOffer: CulturalOffer[] = [
      {
        id: 1,
        title: 'offer',
        description: 'desc',
        address: 'adress',
        subTypeName: 'akva',
        offerType: 'park',
        imageUrls: ['img1', 'img2'],
        latitude: 20,
        longitude: 40
      },
      {
        id: 2,
        title: 'offer2',
        description: 'desc2',
        address: 'adress2',
        subTypeName: 'akva2',
        offerType: 'park2',
        imageUrls: ['img1', 'img2'],
        latitude: 21,
        longitude: 42
      }];

    service.getAll().subscribe(data => {
      offer = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers');
    expect(req.request.method).toBe('GET');
    req.flush(mockOffer);

    tick();

    expect(offer.length).toEqual(2);
    expect(offer[0].id).toEqual(1);
    expect(offer[0].title).toEqual('offer');
    expect(offer[0].description).toEqual('desc');
    expect(offer[0].address).toEqual('adress');
    expect(offer[0].subTypeName).toEqual('akva');
    expect(offer[0].offerType).toEqual('park');
    expect(offer[0].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[0].latitude).toEqual(20);
    expect(offer[0].longitude).toEqual(40);

    expect(offer[1].id).toEqual(2);
    expect(offer[1].title).toEqual('offer2');
    expect(offer[1].description).toEqual('desc2');
    expect(offer[1].address).toEqual('adress2');
    expect(offer[1].subTypeName).toEqual('akva2');
    expect(offer[1].offerType).toEqual('park2');
    expect(offer[1].imageUrls).toEqual(['img1', 'img2']);
    expect(offer[1].latitude).toEqual(21);
    expect(offer[1].longitude).toEqual(42);

  }));

  it('getLocationDetails() should return geo location for adress', fakeAsync(() => {
    let offer: CulturalOffer[];

    let geoLoc = { latitude: 0, longitude: 0 };

    const mockGeo = { latitude: 21, longitude: 42 }

    service.getLocationDetails('neka_adresa').subscribe(data => {
      geoLoc = data;
    });

    const req = httpMock.expectOne('https://nominatim.openstreetmap.org/search?q=neka_adresa&format=json');
    expect(req.request.method).toBe('GET');
    req.flush(mockGeo);

    tick();

    expect(geoLoc).toEqual({ latitude: 21, longitude: 42 });

  }));

  it('getOne() should return one offer', fakeAsync(() => {
    let offer: CulturalOffer;

    const mockOffer: CulturalOffer =
    {
      id: 1,
      title: 'offer',
      description: 'desc',
      address: 'adress',
      subTypeName: 'akva',
      offerType: 'park',
      imageUrls: ['img1', 'img2'],
      latitude: 20,
      longitude: 40
    };

    service.getOne(1).subscribe(data => {
      offer = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockOffer);

    tick();

    expect(offer.id).toEqual(1);
    expect(offer.title).toEqual('offer');
    expect(offer.description).toEqual('desc');
    expect(offer.address).toEqual('adress');
    expect(offer.subTypeName).toEqual('akva');
    expect(offer.offerType).toEqual('park');
    expect(offer.imageUrls).toEqual(['img1', 'img2']);
    expect(offer.latitude).toEqual(20);
    expect(offer.longitude).toEqual(40);

  }));

  it('add() should query url and save a offer', fakeAsync(() => {
    let offer: CulturalOffer = {
      title: 'offer',
      description: 'desc',
      address: 'adress',
      subTypeName: 'akva',
      offerType: 'park',
      imageUrls: ['img1', 'img2'],
      latitude: 20,
      longitude: 40
    };

    const mockOffer: CulturalOffer =
    {
      id: 1,
      title: 'offer',
      description: 'desc',
      address: 'adress',
      subTypeName: 'akva',
      offerType: 'park',
      imageUrls: ['img1', 'img2'],
      latitude: 20,
      longitude: 40
    };

    service.add(offer).subscribe(data => {
      offer = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers');
    expect(req.request.method).toBe('POST');
    req.flush(mockOffer);

    tick();

    expect(offer).toBeDefined();
    expect(offer.id).toEqual(1);
    expect(offer.title).toEqual('offer');
    expect(offer.description).toEqual('desc');
    expect(offer.address).toEqual('adress');
    expect(offer.subTypeName).toEqual('akva');
    expect(offer.offerType).toEqual('park');
    expect(offer.imageUrls).toEqual(['img1', 'img2']);
    expect(offer.latitude).toEqual(20);
    expect(offer.longitude).toEqual(40);

  }));

  it('update() should query url and update a offer', fakeAsync(() => {
    let offer: CulturalOffer = {
      title: 'offer',
      description: 'desc',
      address: 'adress',
      subTypeName: 'akva',
      offerType: 'park',
      imageUrls: ['img1', 'img2'],
      latitude: 20,
      longitude: 40
    };

    const mockOffer: CulturalOffer =
    {
      id: 1,
      title: 'offer',
      description: 'desc',
      address: 'adress',
      subTypeName: 'akva',
      offerType: 'park',
      imageUrls: ['img1', 'img2'],
      latitude: 20,
      longitude: 40
    };

    service.update(1, offer).subscribe(data => {
      offer = data;
    });

    const req = httpMock.expectOne('http://localhost:8080/offers/1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockOffer);

    tick();

    expect(offer).toBeDefined();
    expect(offer.id).toEqual(1);
    expect(offer.title).toEqual('offer');
    expect(offer.description).toEqual('desc');
    expect(offer.address).toEqual('adress');
    expect(offer.subTypeName).toEqual('akva');
    expect(offer.offerType).toEqual('park');
    expect(offer.imageUrls).toEqual(['img1', 'img2']);
    expect(offer.latitude).toEqual(20);
    expect(offer.longitude).toEqual(40);

  }));

  it('delete() should query url and delete a offer', () => {
    service.delete(1).subscribe(res => { });
    
    const req = httpMock.expectOne('http://localhost:8080/offers/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

});
