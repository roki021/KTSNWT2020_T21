import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './maps/map/map.component';
import { CulturalOfferService } from './maps/services/cultural-offer.service';
import { HttpClientModule } from '@angular/common/http';
import { GenTableComponent } from './maps/gen-table/gen-table.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TestComponent } from './maps/testC/test.component';
import { SafeHtmlPipe } from './pipes/safe-html-pipe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
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
