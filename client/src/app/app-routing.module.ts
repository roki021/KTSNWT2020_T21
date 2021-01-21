import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MapComponent } from './maps/map/map.component';
import { TestComponent } from './maps/testC/test.component';
import { LoginFormComponent } from './maps/login-form/login-form/login-form.component';


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
    path: 'login',
    component: LoginFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
