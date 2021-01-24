import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { CulturalOffer } from '../model/cultural-offer';
import { Subscription } from '../model/subscription';
import { AuthService } from '../services/auth.service';
import { GradesService } from '../services/grades.service';
import { SubscriptionService } from '../services/subscription.service';
import { ToastService } from '../toasts/toast-service';

@Component({
  selector: 'app-offer-view',
  templateUrl: './offer-view.component.html',
  styleUrls: ['./offer-view.component.sass']
})
export class OfferViewComponent implements OnInit, OnChanges {

  @Input() selectedOffer: CulturalOffer;
  btnSubType = 'btn-primary';
  btnSubText = 'Subscribe';
  isSubed: boolean;
  overallGrade = 0;
  active = 3;

  constructor(private subsService: SubscriptionService, private authService: AuthService,
              private router: Router, private toastService: ToastService, private gradesService: GradesService) { }

  ngOnInit(): void { }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.selectedOffer) {
      if (!this.isAdmin()) {
        this.checkSubscription();
      }
      this.setAvgGrade();
    }
  }

  setAvgGrade(): void {
    if (this.selectedOffer) {
      this.gradesService.getAvgGrade(this.selectedOffer.id).subscribe(
        res => {
          this.overallGrade = res;
        }
      );
    }
  }

  checkSubscription(): void {
    if (this.authService.isLoggedIn()) {
      this.subsService.getUserSubscriptions().subscribe(
        res => {
          const subscriptions = res;
          let found = false;
          for (const sub of subscriptions) {
            if (sub.culturalOfferId === this.selectedOffer?.id) {
              this.subscribeButtons(true);
              this.isSubed = true;
              found = true;
              break;
            }
          }

          if (!found) {
            this.subscribeButtons(false);
            this.isSubed = false;
          }
        }
      );
    } else {
      this.subscribeButtons(false);
      this.isSubed = false;
    }
  }

  subscribeButtons(check: boolean) {
    if (check) {
      this.btnSubType = 'btn-secondary';
      this.btnSubText = 'Unsubscribe';
    } else {
      this.btnSubType = 'btn-primary';
      this.btnSubText = 'Subscribe';
    }
  }

  toggleSubscription(): void {
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['login']);
    } else {

      if (this.isAdmin()) {
        return;
      }

      if (this.isSubed) {
        const subscription: Subscription = {
          culturalOfferId: this.selectedOffer.id,
          guestId: this.authService.getUserId(),
          culturalOfferTitle: this.selectedOffer.title
        };
        this.subsService.unsubscribe(subscription).subscribe(
          res => {
            this.subscribeButtons(false);
            this.isSubed = false;
            this.showSuccess('Successfully unsubscribed!');
          }
        );
      } else {
        const subscription: Subscription = {
          culturalOfferId: this.selectedOffer.id,
          guestId: this.authService.getUserId(),
          culturalOfferTitle: this.selectedOffer.title
        };
        this.subsService.subscribe(subscription).subscribe(
          res => {
            this.subscribeButtons(true);
            this.isSubed = true;
            this.showSuccess('Successfully subscribed!');
          }
        );
      }
    }
  }

  showSuccess(message: string) {
    this.toastService.show(message, { classname: 'bg-info text-light', delay: 4000 });
  }

  showDanger() {
    this.toastService.show('Unsubscription failed', { classname: 'bg-danger text-light', delay: 4000 });
  }

  isAdmin() {
    return this.authService.getRole() === 'ROLE_ADMIN';
  }
}
