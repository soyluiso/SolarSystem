import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IPlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';
import { PlanetMySuffixService } from './planet-my-suffix.service';
import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';
import { SolarSystemMySuffixService } from 'app/entities/solar-system-my-suffix';

@Component({
    selector: 'jhi-planet-my-suffix-update',
    templateUrl: './planet-my-suffix-update.component.html'
})
export class PlanetMySuffixUpdateComponent implements OnInit {
    private _planet: IPlanetMySuffix;
    isSaving: boolean;

    solarsystems: ISolarSystemMySuffix[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private planetService: PlanetMySuffixService,
        private solarSystemService: SolarSystemMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ planet }) => {
            this.planet = planet;
        });
        this.solarSystemService.query().subscribe(
            (res: HttpResponse<ISolarSystemMySuffix[]>) => {
                this.solarsystems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.planet.id !== undefined) {
            this.subscribeToSaveResponse(this.planetService.update(this.planet));
        } else {
            this.subscribeToSaveResponse(this.planetService.create(this.planet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPlanetMySuffix>>) {
        result.subscribe((res: HttpResponse<IPlanetMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackSolarSystemById(index: number, item: ISolarSystemMySuffix) {
        return item.id;
    }
    get planet() {
        return this._planet;
    }

    set planet(planet: IPlanetMySuffix) {
        this._planet = planet;
    }
}
