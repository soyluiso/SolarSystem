import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';

@Component({
    selector: 'jhi-planet-my-suffix-detail',
    templateUrl: './planet-my-suffix-detail.component.html'
})
export class PlanetMySuffixDetailComponent implements OnInit {
    planet: IPlanetMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planet }) => {
            this.planet = planet;
        });
    }

    previousState() {
        window.history.back();
    }
}
