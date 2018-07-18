import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

@Component({
    selector: 'jhi-solar-system-my-suffix-detail',
    templateUrl: './solar-system-my-suffix-detail.component.html'
})
export class SolarSystemMySuffixDetailComponent implements OnInit {
    solarSystem: ISolarSystemMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ solarSystem }) => {
            this.solarSystem = solarSystem;
        });
    }

    previousState() {
        window.history.back();
    }
}
