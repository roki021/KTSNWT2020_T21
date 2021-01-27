import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { AddCommentService } from '../services/add-comment.service';

import { AddCommentComponent } from './add-comment.component';

describe('AddCommentComponent', () => {
  let component: AddCommentComponent;
  let fixture: ComponentFixture<AddCommentComponent>;
  let addCommentService: AddCommentService;
  let fb : FormBuilder;

  beforeEach(async(() => {
    const authServiceMock = {
      addCommentCall: jasmine.createSpy('addCommentCall')
        .and.returnValue(of({body: {
          id: 1,
          content:"test",
          commentedOn: new Date(),
          imageUrls: [],
          userId:1,
          culturalOfferId:1
        }}))
    };

    TestBed.configureTestingModule({
      declarations: [ AddCommentComponent ],
      imports: [
        HttpClientTestingModule,
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule
      ],
      providers: [{ provide: AddCommentService, useValue: authServiceMock }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddCommentComponent);
    component = fixture.componentInstance;
    addCommentService = TestBed.inject(AddCommentService);
    fb = TestBed.inject(FormBuilder);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCommentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('valid comment add', () => {
    component.ngOnInit();

    component.myForm = fb.group({
      content:"test",
      file: '',
      fileSource: ''
    });

    component.submit();
    expect(addCommentService.addCommentCall).toHaveBeenCalled();

  });

  it('invalid comment add', () => {
    component.ngOnInit();

    component.myForm = fb.group({
      content: '',
      file: '',
      fileSource: ''
    });

    component.submit();
    expect(addCommentService.addCommentCall).not.toHaveBeenCalled();

  });
});
