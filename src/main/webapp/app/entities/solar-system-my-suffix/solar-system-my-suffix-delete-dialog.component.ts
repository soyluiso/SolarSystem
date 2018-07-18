import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';
import { SolarSystemMySuffixService } from './solar-system-my-suffix.service';

@Component({
    selector: 'jhi-solar-system-my-suffix-delete-dialog',
    templateUrl: './solar-system-my-suffix-delete-dialog.component.html'
})
export class SolarSystemMySuffixDeleteDialogComponent {
    solarSystem: ISolarSystemMySuffix;

    constructor(
        private solarSystemService: SolarSystemMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solarSystemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'solarSystemListModification',
                content: 'Deleted an solarSystem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solar-system-my-suffix-delete-popup',
    template: ''
})
export class SolarSystemMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ solarSystem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SolarSystemMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.solarSystem = solarSystem;
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
