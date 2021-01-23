import { Component, OnInit } from '@angular/core';
import Map from 'ol/Map';
import Tile from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import View from 'ol/View';
import { fromLonLat, toLonLat } from 'ol/proj.js';
import Style from 'ol/style/Style';
import Stroke from 'ol/style/Stroke';
import Text from 'ol/style/Text';
import Fill from 'ol/style/Fill';
import Icon from 'ol/style/Icon';
import Point from 'ol/geom/Point';
import Feature from 'ol/Feature';
import LineString from 'ol/geom/LineString';
import Vector from 'ol/layer/Vector';
import * as source from 'ol/source';
import { equals } from 'ol/extent';
import { CulturalOfferService } from '../services/cultural-offer.service';
import { CulturalOffer } from '../model/cultural-offer';
import * as olProj from 'ol/proj';
import { Zoom } from '../model/zoom';
import { error } from 'protractor';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.sass']
})
export class MapComponent implements OnInit {

  map;
  vector;
  cashed_maps_extent = [];
  cash_flow_id = 0;
  curr_id = 0;
  cultural_offers: CulturalOffer[];
  zoom: Zoom;
  cash_features: Feature[] = [];
  pageSize: number;
  currentPage: number;
  totalSize: number;
  searchActive: boolean = false;
  searchField = "grade";
  searchValue = "1";

  constructor(private cultural_offer_service: CulturalOfferService) {
    this.pageSize = 2;
    this.currentPage = 1;
    this.totalSize = 1;
  }

  ngOnInit(): void {
    this.initilizeMap();
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    if (this.searchActive) {
      this.search(newPage);
    }
    else {
      this.loadAll(newPage);
    }

  }

  loadAll(newPage: number) {
    this.cultural_offer_service.getPage(newPage - 1, this.pageSize).subscribe(
      res => {
        this.cultural_offers = res.body as CulturalOffer[];
        this.totalSize = Number(res.headers.get('Total-pages'));
        this.createFeatures();
      }
    );
  }

  search(newPage: number) {
    this.cultural_offer_service.search(this.searchField, this.searchValue,
      this.currentPage - 1, this.pageSize).subscribe(
        res => {
          this.cultural_offers = res.body as CulturalOffer[];
          this.totalSize = Number(res.headers.get('Total-pages'));
          this.createFeatures();
        },
        error => {

        }
      );
  }

  createFeatures() {
    this.vector.getSource().clear();
    for (let i = 0; i < this.cultural_offers.length; i++) {
      const coord1 = olProj.fromLonLat([this.cultural_offers[i].longitude, this.cultural_offers[i].latitude]);
      const marker1 = new Point(coord1);
      const featureMarker1 = new Feature(marker1);
      const styleMarker = new Style({
        image: new Icon({
          anchor: [12, 40],
          anchorXUnits: 'pixels',
          anchorYUnits: 'pixels',
          opacity: 1,
          src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABBBJREFUeNrkW01IVFEUPuMMk5qlaEkgKKERiBGG/dAPjrtmRmibJQWRmxZRbSJooWRE7ZptYVBUqyhJp0WQShFZRkISRklgSfZjaKiNkzPTOeNrEY1273v3nvvMA4fL6Dnnnvt937sz980bTyqVgqVsWbDEzWcnyePxSMUHQ2EvDjvR69C3oK9HX4Oea4VMo4+iv0Z/it6F/uhetDMhM48dNXtsJQkCgAsvxeEYeiP6aslpvqBfQ7+IQAwvKgBw4UU4tKI3oXsdqpRUcAn9NAIx5noAcPF7cGhDL1R8uX5DP4QgtLsSAFw4/fEc+knN+9Z59FMIRMo1AODi6d3kKvp+ps37OvoBBCHpFABVb4MRxsWDNVdERSHHCkD2D1ublAlrQhVcNnYJ4OLLcXiJnmMIgB/oGxCEIVOXQMTg4sGaO2LkEkD2A9YnNjdYHaqg285afA4mPW4nyYsfiyrL41BVEYeigrlNfGw8Cwbe+uHVkB8SCdu9dLMpIBSuL8ZhRAZA2jZqa2Kwr34SVhVkXuXXcS/c6MiDnr5skGxrFr0k2tnxmWsPCMos3udNwYmDE3C0cWLexZPR/yiGYilHUslBzuNwQCb4SMN32FEdE46nWMrR2ZNTADaKBm6qnIHA5pg8wphDuTp6UgFAmWjg3uCU7V1WMreMEwChk15xYQIqSn/aBoByqYbKnnSdBTLa2pJZV9TQAYBQV/krk44blKgxywnAiEjQ1LTHMQASNUY4ARgUCXo/6nMMgESNQU4AnogEDX/0wacx+7cEKZdqqOxJFQBR0cBb95fbBkAyN8oGAJ686N59j0jsg96c9CFH1iiHcgWtx+qJ9W2wWSQoiZv4hbZ8ePdBfD+gWMpJJtX2ovR+QPpEFArTEbRWJCfbn4KG8CSEdk2nj8SZjI7C0Ye5cLMzD2Jxjwz7Abt3hJxu02dEAaAFXbm9Au525cL26hmoWheHovy5T3ljE14YeOOHxy+WpY/EOpSoRQGWCmj33WroTlAvsr/t9wtTt8WbwZw5nlvVFyMmVPAH+yYVYEoFSuZU+d0gpwr+Yt+0ArhVoGwupV+PM6kgI/tuUACXCpTOoeMBCZ0qmJd9tyhAtwqU19b1jNAzHGoU9/oc2V+wplsUQHZWQ81WHY3qAoAeZOpXWK/fqrk4ALAeYGpRWLIl00NRblaAShVoY18rAApVoI193QpQoQKt7GsHQIEKtLLPoQAnKtDOPgsADlSgnX0uBdhRAQv7bADYUAEL+5wKkFEBG/usAEiogI19bgWIqICVfXYABFTAyr4JBSykAnb2jQCwgArY2TelgEwqMMK+MQAyqMAI+2Q+MGfEeJ/VQ7upJrT+cvRfFgyFd+OQjezfUVGP7TdD/5Mt+V+P/xJgAO0Axv5zXgpBAAAAAElFTkSuQmCC'
        }),
        text: new Text({
          text: this.cultural_offers[i].title,
          scale: 1.2,
          fill: new Fill({
            color: '#fff'
          }),
          stroke: new Stroke({
            color: '0',
            width: 3
          })
        })
      });
      featureMarker1.setStyle(styleMarker);
      this.vector.getSource().addFeature(featureMarker1);

    }
  }

  initilizeMap() {
    const lineStyle = new Style({
      stroke: new Stroke({ color: '#ffcc33', width: 3 })
    });
    const styleMarker = new Style({
      image: new Icon({
        scale: .7, anchor: [0.5, 1],
        src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABBBJREFUeNrkW01IVFEUPuMMk5qlaEkgKKERiBGG/dAPjrtmRmibJQWRmxZRbSJooWRE7ZptYVBUqyhJp0WQShFZRkISRklgSfZjaKiNkzPTOeNrEY1273v3nvvMA4fL6Dnnnvt937sz980bTyqVgqVsWbDEzWcnyePxSMUHQ2EvDjvR69C3oK9HX4Oea4VMo4+iv0Z/it6F/uhetDMhM48dNXtsJQkCgAsvxeEYeiP6aslpvqBfQ7+IQAwvKgBw4UU4tKI3oXsdqpRUcAn9NAIx5noAcPF7cGhDL1R8uX5DP4QgtLsSAFw4/fEc+knN+9Z59FMIRMo1AODi6d3kKvp+ps37OvoBBCHpFABVb4MRxsWDNVdERSHHCkD2D1ublAlrQhVcNnYJ4OLLcXiJnmMIgB/oGxCEIVOXQMTg4sGaO2LkEkD2A9YnNjdYHaqg285afA4mPW4nyYsfiyrL41BVEYeigrlNfGw8Cwbe+uHVkB8SCdu9dLMpIBSuL8ZhRAZA2jZqa2Kwr34SVhVkXuXXcS/c6MiDnr5skGxrFr0k2tnxmWsPCMos3udNwYmDE3C0cWLexZPR/yiGYilHUslBzuNwQCb4SMN32FEdE46nWMrR2ZNTADaKBm6qnIHA5pg8wphDuTp6UgFAmWjg3uCU7V1WMreMEwChk15xYQIqSn/aBoByqYbKnnSdBTLa2pJZV9TQAYBQV/krk44blKgxywnAiEjQ1LTHMQASNUY4ARgUCXo/6nMMgESNQU4AnogEDX/0wacx+7cEKZdqqOxJFQBR0cBb95fbBkAyN8oGAJ686N59j0jsg96c9CFH1iiHcgWtx+qJ9W2wWSQoiZv4hbZ8ePdBfD+gWMpJJtX2ovR+QPpEFArTEbRWJCfbn4KG8CSEdk2nj8SZjI7C0Ye5cLMzD2Jxjwz7Abt3hJxu02dEAaAFXbm9Au525cL26hmoWheHovy5T3ljE14YeOOHxy+WpY/EOpSoRQGWCmj33WroTlAvsr/t9wtTt8WbwZw5nlvVFyMmVPAH+yYVYEoFSuZU+d0gpwr+Yt+0ArhVoGwupV+PM6kgI/tuUACXCpTOoeMBCZ0qmJd9tyhAtwqU19b1jNAzHGoU9/oc2V+wplsUQHZWQ81WHY3qAoAeZOpXWK/fqrk4ALAeYGpRWLIl00NRblaAShVoY18rAApVoI193QpQoQKt7GsHQIEKtLLPoQAnKtDOPgsADlSgnX0uBdhRAQv7bADYUAEL+5wKkFEBG/usAEiogI19bgWIqICVfXYABFTAyr4JBSykAnb2jQCwgArY2TelgEwqMMK+MQAyqMAI+2Q+MGfEeJ/VQ7upJrT+cvRfFgyFd+OQjezfUVGP7TdD/5Mt+V+P/xJgAO0Axv5zXgpBAAAAAElFTkSuQmCC'
      })
    });
    this.vector = new Vector({
      source: new source.Vector({
        features: [],
      }),
      style: [lineStyle, styleMarker],
    });

    this.map = new Map({
      target: 'map',
      layers: [
        new Tile({
          source: new OSM({
          }),
        }),
        this.vector
      ],
      view: new View({
        center: fromLonLat([22, 44.1]),
        zoom: 7.1
      })
    });
    this.map.setView(
      new View({
        center: fromLonLat([22, 44.1]),
        zoom: 7.1,
        extent: this.map.getView().calculateExtent(this.map.getSize())
      })
    );

    this.changePage(this.currentPage);

    this.map.on('click', (e) => {
      this.map.forEachFeatureAtPixel(e.pixel,
        (feature) => {
          console.log("Feature pogodjen");
          // OVDE POZIVATE PROZOR KAD SE PRITISNE CULTURAL OFFER!!!
        },
        { hitTolerance: 0 }
      );
    });
  }
}
