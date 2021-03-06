import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCulturalOfferComponent } from './maps/cultural-offers/add-cultural-offer/add-cultural-offer.component';
import { AllOffersComponent } from './maps/cultural-offers/all-offers/all-offers.component';
import { MapComponent } from './maps/map/map.component';
import { OfferTypeListComponent } from './maps/types/offer-type-list/offer-type-list.component';
import { SubtypeListComponent } from './maps/types/subtype-list/subtype-list.component';
import { TestComponent } from './maps/testC/test.component';
import { LoginFormComponent } from './maps/login-form/login-form/login-form.component';
import { RolesGuard } from './guards/roles.guard';
import { LoginGuard } from './guards/login.guard';
import { RegistrationComponent } from './maps/registration/registration.component';

import { ProfileComponent } from './maps/profile/profile.component';
import { AllOfferNewsComponent } from './maps/news/all-offer-news/all-offer-news.component';
import { GradingComponent } from './maps/grading/grading.component';
import { CommentCardsComponent } from './maps/comment-cards/comment-cards.component';
import { AddCommentComponent } from './maps/add-comment/add-comment.component';
import { NotFoundComponent } from './maps/not-found/not-found.component';
import { ConfirmRegComponent } from './maps/confirm-reg/confirm-reg.component';
import { RegistrationFollowupComponent } from './maps/registration-followup/registration-followup.component';

const routes: Routes = [{
  path: '',
  component: MapComponent
},
{
  path: 'all_offers',
  component: AllOffersComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'offer-types',
  component: OfferTypeListComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'offer-types/:id/subtypes',
  component: SubtypeListComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'login',
  component: LoginFormComponent,
  canActivate: [LoginGuard]
},
{
  path: 'profile',
  component: ProfileComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_GUEST' }
},
{
  path: 'register',
  component: RegistrationComponent,
  canActivate: [LoginGuard]
},
{
  path: 'news/:offer_id',
  component: AllOfferNewsComponent,
  canActivate: [RolesGuard],
  data: {expectedRoles: 'ROLE_ADMIN'}
},
{
  path: 'registrationConfirm',
  component: ConfirmRegComponent
},
{
  path: "postregistration",
  component: RegistrationFollowupComponent
},
{
  path: '**',
  component: NotFoundComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
