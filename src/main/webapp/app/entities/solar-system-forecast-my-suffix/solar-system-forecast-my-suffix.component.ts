import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';
import { Principal } from 'app/core';
import { SolarSystemForecastMySuffixService } from './solar-system-forecast-my-suffix.service';

@Component({
    selector: 'jhi-solar-system-forecast-my-suffix',
    templateUrl: './solar-system-forecast-my-suffix.component.html'
})
export class SolarSystemForecastMySuffixComponent implements OnInit, OnDestroy {
    solarSystemForecasts: ISolarSystemForecastMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private solarSystemForecastService: SolarSystemForecastMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.solarSystemForecastService.query().subscribe(
            (res: HttpResponse<ISolarSystemForecastMySuffix[]>) => {
                this.solarSystemForecasts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSolarSystemForecasts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISolarSystemForecastMySuffix) {
        return item.id;
    }

    registerChangeInSolarSystemForecasts() {
        this.eventSubscriber = this.eventManager.subscribe('solarSystemForecastListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
