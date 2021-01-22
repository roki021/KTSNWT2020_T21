import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapComponent } from './maps/map/map.component';
import { OfferTypeListComponent } from './maps/types/offer-type-list/offer-type-list.component';
import { SubtypeListComponent } from './maps/types/subtype-list/subtype-list.component';
import { TestComponent } from './maps/testC/test.component';
import { LoginFormComponent } from './maps/login-form/login-form/login-form.component';
import { RolesGuard } from './guards/roles.guard';
import { LoginGuard } from './guards/login.guard';
import { RegistrationComponent } from './maps/registration/registration.component';

import { ProfileComponent } from './maps/profile/profile.component';


const routes: Routes = [{
  path: '',
  component: MapComponent
},
{
  path: 'offer-types',
  component: OfferTypeListComponent,
  canActivate: [RolesGuard],
  data: {expectedRoles: 'ROLE_ADMIN'}
},
{
  path: 'offer-types/:id/subtypes',
  component: SubtypeListComponent,
  canActivate: [RolesGuard],
  data: {expectedRoles: 'ROLE_ADMIN'}
},
{
  path: 'table',
  component: TestComponent
},
{
  path: 'login',
  component: LoginFormComponent,
  canActivate: [LoginGuard]
},
{
  path: 'profile',
  component: ProfileComponent
},
{
  path: 'register',
  component: RegistrationComponent,
  canActivate: [LoginGuard]
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
