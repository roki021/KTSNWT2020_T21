import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommentGalleryComponent } from './comment-gallery.component';

describe('CommentGalleryComponent', () => {
  let component: CommentGalleryComponent;
  let fixture: ComponentFixture<CommentGalleryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommentGalleryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommentGalleryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
