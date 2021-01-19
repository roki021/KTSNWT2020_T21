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
import { SubtypeListComponent } from './maps/types/subtype-list/subtype-list.component';
import { SubtypeTableComponent } from './maps/types/subtype-table/subtype-table.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { GenTableComponent } from './maps/gen-table/gen-table.component';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TestComponent } from './maps/testC/test.component';
import { SafeHtmlPipe } from './pipes/safe-html-pipe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    PaginationComponent,
    OfferTypeListComponent,
    OfferTypeTableComponent,
    SubtypeListComponent,
    SubtypeTableComponent,
    GenTableComponent,
    TestComponent,
    SafeHtmlPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    FontAwesomeModule
  ],
  providers: [CulturalOfferService],
  bootstrap: [AppComponent]
})
export class AppModule { }
