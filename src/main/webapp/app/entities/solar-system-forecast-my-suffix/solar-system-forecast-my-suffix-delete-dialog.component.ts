import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';
import { SolarSystemForecastMySuffixService } from './solar-system-forecast-my-suffix.service';

@Component({
    selector: 'jhi-solar-system-forecast-my-suffix-delete-dialog',
    templateUrl: './solar-system-forecast-my-suffix-delete-dialog.component.html'
})
export class SolarSystemForecastMySuffixDeleteDialogComponent {
    solarSystemForecast: ISolarSystemForecastMySuffix;

    constructor(
        private solarSystemForecastService: SolarSystemForecastMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solarSystemForecastService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'solarSystemForecastListModification',
                content: 'Deleted an solarSystemForecast'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solar-system-forecast-my-suffix-delete-popup',
    template: ''
})
export class SolarSystemForecastMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ solarSystemForecast }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SolarSystemForecastMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.solarSystemForecast = solarSystemForecast;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
