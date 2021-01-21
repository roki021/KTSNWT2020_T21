import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapComponent } from './maps/map/map.component';
import { OfferTypeListComponent } from './maps/types/offer-type-list/offer-type-list.component';
import { SubtypeListComponent } from './maps/types/subtype-list/subtype-list.component';
import { TestComponent } from './maps/testC/test.component';
import { LoginFormComponent } from './maps/login-form/login-form/login-form.component';


const routes: Routes = [{
  path: '',
  component: MapComponent
},
{
  path: 'offer-types',
  component: OfferTypeListComponent
},
{
  path: 'offer-types/:id/subtypes',
  component: SubtypeListComponent
},
{
  path: 'table',
  component: TestComponent
},
{
  path: 'login',
  component: LoginFormComponent
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
