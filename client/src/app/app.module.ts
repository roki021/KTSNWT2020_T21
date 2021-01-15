import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './maps/map/map.component';
import { CulturalOfferService } from './maps/services/cultural-offer.service';
import { HttpClientModule } from '@angular/common/http';
import { PaginationComponent } from './maps/pagination/pagination.component';
import { OfferTypeListComponent } from './maps/types/offer-type-list/offer-type-list.component';
import { OfferTypeTableComponent } from './maps/types/offer-type-table/offer-type-table.component';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    PaginationComponent,
    OfferTypeListComponent,
    OfferTypeTableComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [CulturalOfferService],
  bootstrap: [AppComponent]
})
export class AppModule { }
