import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ChangePasswordComponent } from '../change-password/change-password.component';
import { Guest } from '../model/guest';
import { AuthService } from '../services/auth.service';
import { ProfileService } from '../services/profile.service';
import { faMapMarkedAlt } from '@fortawesome/free-solid-svg-icons';
import { Subscription } from '../model/subscription';
import { SubscriptionService } from '../services/subscription.service';
import { TableHeader } from '../gen-table/table-header';
import { TableOperation } from '../gen-table/table-operation';
import { Icons } from 'src/app/enums/icons.enum';
import { ToastService } from '../toasts/toast-service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.sass']
})
export class ProfileComponent implements OnInit {

  guestId: number;
  guest: Guest = { id: null, username: '', emailAddress: '', firstName: '', lastName: '', password: 'ignore' };
  subscriptions: Subscription[] = [];
  updateBadRequest = false;
  badRequest = false;
  unauthorized = false;
  notFound = false;
  faMarkedMap = faMapMarkedAlt;

  tableHeader: TableHeader[] = [
    {
      headerName: 'Cultural offer',
      fieldName: ['culturalOfferTitle']
    }
  ];

  operations: TableOperation<Subscription>[] = [
    {
      operation: (item: Subscription) => this.unsubscribe(item),
      icon: Icons.slashPreview
    }
  ];

  constructor(
    private profileService: ProfileService, private modalService: NgbModal,
    private authService: AuthService, private subsService: SubscriptionService,
    private toastService: ToastService) { }

  ngOnInit(): void {
    this.guestId = this.authService.getUserId();
    this.load();
    this.loadSubscriptions();
  }

  showSuccess(title: string) {
    this.toastService.show(`Successfully unsubscribed from ${title} offer`, { classname: 'bg-success text-light', delay: 4000 });
  }

  showDanger() {
    this.toastService.show('Unsubscription failed', { classname: 'bg-danger text-light', delay: 4000 });
  }

  unsubscribe(subscription: Subscription): void {
    this.subsService.unsubscribe(subscription).subscribe(
      res => {
        if (res.deleted) {
          this.loadSubscriptions();
          this.showSuccess(subscription.culturalOfferTitle);
        }
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        } else if (error.status === 404) {
          this.notFound = true;
        }
        this.showDanger();
      }
    );
  }

  load(): void {
    this.profileService.getProfile(this.guestId).subscribe(
      res => {
        this.guest = res.body as Guest;
        this.badRequest = false;
        this.notFound = false;
        this.unauthorized = false;
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        } else if (error.status === 404) {
          this.notFound = true;
        }
      }
    );
  }

  loadSubscriptions(): void {
    this.subsService.getUserSubscriptions().subscribe(
      res => {
        this.subscriptions = res;
      },
      error => {
        if (error.status === 400) {
          this.badRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        } else if (error.status === 404) {
          this.notFound = true;
        }
      }
    );
  }

  changePassword() {
    const modalRef = this.modalService.open(ChangePasswordComponent, { ariaLabelledBy: 'change-password', size: 'lg', scrollable: true });
    modalRef.componentInstance.guestId = this.guestId;
  }

  update() {
    this.guest.password = 'ignore';
    this.profileService.update(this.guest, this.guestId).subscribe(
      res => {
        this.guest = res.body as Guest;
        this.updateBadRequest = false;
        this.notFound = false;
        this.unauthorized = false;
        this.authService.refreshToken().subscribe((refreshed: boolean) => {
          if (refreshed) {
            console.log(this.authService.getToken());
          }
        });
      },
      error => {
        if (error.status === 400) {
          this.updateBadRequest = true;
        } else if (error.status === 401 || error.status === 403) {
          this.unauthorized = true;
        } else if (error.status === 404) {
          this.notFound = true;
        }
      }
    );
  }

}
