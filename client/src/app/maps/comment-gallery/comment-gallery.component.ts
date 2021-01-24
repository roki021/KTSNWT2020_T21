import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-comment-gallery',
  templateUrl: './comment-gallery.component.html',
  styleUrls: ['./comment-gallery.component.sass']
})
export class CommentGalleryComponent implements OnInit {
  @Input() public imageUrls : string[];

  constructor(private modalService: NgbModal) {}

  ngOnInit(): void {
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      
    }, (reason) => {
      
    });
  }

}
