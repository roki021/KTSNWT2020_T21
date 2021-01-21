import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCulturalOfferComponent } from './maps/add-cultural-offer/add-cultural-offer.component';
import { AllOffersComponent } from './maps/all-offers/all-offers.component';
import { MapComponent } from './maps/map/map.component';
import { TestComponent } from './maps/testC/test.component';


const routes: Routes = [
  {
    path: '',
    component: MapComponent
  },
  {
    path: 'table',
    component: TestComponent
  },
  {
    path: 'all_offers',
    component: AllOffersComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
