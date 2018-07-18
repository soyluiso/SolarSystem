import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';
import { SolarSystemForecastMySuffixService } from './solar-system-forecast-my-suffix.service';
import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';
import { SolarSystemMySuffixService } from 'app/entities/solar-system-my-suffix';

@Component({
    selector: 'jhi-solar-system-forecast-my-suffix-update',
    templateUrl: './solar-system-forecast-my-suffix-update.component.html'
})
export class SolarSystemForecastMySuffixUpdateComponent implements OnInit {
    private _solarSystemForecast: ISolarSystemForecastMySuffix;
    isSaving: boolean;

    solarsystems: ISolarSystemMySuffix[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private solarSystemForecastService: SolarSystemForecastMySuffixService,
        private solarSystemService: SolarSystemMySuffixService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ solarSystemForecast }) => {
            this.solarSystemForecast = solarSystemForecast;
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
        if (this.solarSystemForecast.id !== undefined) {
            this.subscribeToSaveResponse(this.solarSystemForecastService.update(this.solarSystemForecast));
        } else {
            this.subscribeToSaveResponse(this.solarSystemForecastService.create(this.solarSystemForecast));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISolarSystemForecastMySuffix>>) {
        result.subscribe(
            (res: HttpResponse<ISolarSystemForecastMySuffix>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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
    get solarSystemForecast() {
        return this._solarSystemForecast;
    }

    set solarSystemForecast(solarSystemForecast: ISolarSystemForecastMySuffix) {
        this._solarSystemForecast = solarSystemForecast;
    }
}
