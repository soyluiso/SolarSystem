import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';
import { SolarSystemMySuffixService } from './solar-system-my-suffix.service';

@Component({
    selector: 'jhi-solar-system-my-suffix-update',
    templateUrl: './solar-system-my-suffix-update.component.html'
})
export class SolarSystemMySuffixUpdateComponent implements OnInit {
    private _solarSystem: ISolarSystemMySuffix;
    isSaving: boolean;

    constructor(private solarSystemService: SolarSystemMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ solarSystem }) => {
            this.solarSystem = solarSystem;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.solarSystem.id !== undefined) {
            this.subscribeToSaveResponse(this.solarSystemService.update(this.solarSystem));
        } else {
            this.subscribeToSaveResponse(this.solarSystemService.create(this.solarSystem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISolarSystemMySuffix>>) {
        result.subscribe((res: HttpResponse<ISolarSystemMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get solarSystem() {
        return this._solarSystem;
    }

    set solarSystem(solarSystem: ISolarSystemMySuffix) {
        this._solarSystem = solarSystem;
    }
}
