import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './maps/map/map.component';
import { CulturalOfferService } from './maps/services/cultural-offer.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { GenTableComponent } from './maps/gen-table/gen-table.component';
import { PaginationComponent } from './maps/pagination/pagination.component';
import { OfferTypeListComponent } from './maps/types/offer-type-list/offer-type-list.component';
import { SubtypeListComponent } from './maps/types/subtype-list/subtype-list.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TestComponent } from './maps/testC/test.component';
import { SafeHtmlPipe } from './pipes/safe-html-pipe.pipe';
import { AddCulturalOfferComponent } from './maps/cultural-offers/add-cultural-offer/add-cultural-offer.component';
import { AllOffersComponent } from './maps/cultural-offers/all-offers/all-offers.component';
import { EditCulturalOfferComponent } from './maps/cultural-offers/edit-cultural-offer/edit-cultural-offer.component';
import { LoginFormComponent } from './maps/login-form/login-form/login-form.component';
import { TokenInterceptorService } from './maps/services/token-interceptor.service';
import { RegistrationComponent } from './maps/registration/registration.component';
import { AddOfferTypeComponent } from './maps/types/add-offer-type/add-offer-type.component';
import { AddSubtypeComponent } from './maps/types/add-subtype/add-subtype.component';
import { UpdateOfferTypeComponent } from './maps/types/update-offer-type/update-offer-type.component';
import { UpdateSubtypeComponent } from './maps/types/update-subtype/update-subtype.component';
import { ProfileComponent } from './maps/profile/profile.component';
import { ChangePasswordComponent } from './maps/change-password/change-password.component';
import { OptionNavbarComponent } from './maps/option-navbar/option-navbar.component';
import { GradingComponent } from './maps/grading/grading.component';
import { OfferViewComponent } from './maps/offer-view/offer-view.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastsComponent } from './maps/toasts/toasts-container.component';
import { OfferInfoComponent } from './maps/offer-info/offer-info.component';
import { AddOfferNewsComponent } from './maps/news/add-offer-news/add-offer-news.component';
import { AllOfferNewsComponent } from './maps/news/all-offer-news/all-offer-news.component';
import { EditOfferNewsComponent } from './maps/news/edit-offer-news/edit-offer-news.component';
import { AddCommentComponent } from './maps/add-comment/add-comment.component';
import { CommentCardsComponent } from './maps/comment-cards/comment-cards.component';
import { CommentGalleryComponent } from './maps/comment-gallery/comment-gallery.component';
import { IvyGalleryModule } from 'angular-gallery';


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    PaginationComponent,
    OfferTypeListComponent,
    SubtypeListComponent,
    GenTableComponent,
    TestComponent,
    SafeHtmlPipe,
    AddCulturalOfferComponent,
    AllOffersComponent,
    EditCulturalOfferComponent,
    LoginFormComponent,
    RegistrationComponent,
    AddOfferTypeComponent,
    AddSubtypeComponent,
    UpdateOfferTypeComponent,
    UpdateSubtypeComponent,
    ProfileComponent,
    ChangePasswordComponent,
    OptionNavbarComponent,
    GradingComponent,
    OfferViewComponent,
    AddOfferNewsComponent,
    AllOfferNewsComponent,
    EditOfferNewsComponent,
    ToastsComponent,
    OfferInfoComponent,
    AddCommentComponent,
    CommentCardsComponent,
    CommentGalleryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    FontAwesomeModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    IvyGalleryModule
  ],
  providers: [
    CulturalOfferService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
