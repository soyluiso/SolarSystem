import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

@Component({
    selector: 'jhi-solar-system-forecast-my-suffix-detail',
    templateUrl: './solar-system-forecast-my-suffix-detail.component.html'
})
export class SolarSystemForecastMySuffixDetailComponent implements OnInit {
    solarSystemForecast: ISolarSystemForecastMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ solarSystemForecast }) => {
            this.solarSystemForecast = solarSystemForecast;
        });
    }

    previousState() {
        window.history.back();
    }
}
