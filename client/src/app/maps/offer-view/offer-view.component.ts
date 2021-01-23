import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router } from '@angular/router';
import { NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap';
import { CulturalOffer } from '../model/cultural-offer';
import { Subscription } from '../model/subscription';
import { AuthService } from '../services/auth.service';
import { SubscriptionService } from '../services/subscription.service';
import { ToastService } from '../toasts/toast-service';

@Component({
  selector: 'app-offer-view',
  templateUrl: './offer-view.component.html',
  styleUrls: ['./offer-view.component.sass'],
  providers: [NgbRatingConfig]
})
export class OfferViewComponent implements OnInit, OnChanges {

  @Input() selectedOffer: CulturalOffer;
  btnSubType: string = 'btn-primary';
  btnSubText: string = 'Subscribe';
  isSubed: boolean;
  overallGrade: number = 2.5;
  active: number = 3;

  constructor(private config: NgbRatingConfig, private subsService: SubscriptionService, 
    private authService: AuthService, private router: Router, private toastService: ToastService) {
    config.max = 5;
    config.readonly = true;
  }

  ngOnInit(): void {}
  
  ngOnChanges(changes: SimpleChanges): void {
    this.checkSubscription();
  }

  checkSubscription(): void {
    if (this.authService.isLoggedIn()) {
      this.subsService.getUserSubscriptions().subscribe(
        res => {
          let subscriptions = res;
          let found = false;
          for(let sub of subscriptions) {
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
      if (this.isSubed) {
        let subscription: Subscription = {
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
        let subscription: Subscription = {
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
    this.toastService.show("Unsubscription failed", { classname: 'bg-danger text-light', delay: 4000 });
  }
}
