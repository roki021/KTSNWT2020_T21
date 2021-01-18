import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
