import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';
import { PlanetMySuffixService } from './planet-my-suffix.service';

@Component({
    selector: 'jhi-planet-my-suffix-delete-dialog',
    templateUrl: './planet-my-suffix-delete-dialog.component.html'
})
export class PlanetMySuffixDeleteDialogComponent {
    planet: IPlanetMySuffix;

    constructor(private planetService: PlanetMySuffixService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planetService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'planetListModification',
                content: 'Deleted an planet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-planet-my-suffix-delete-popup',
    template: ''
})
export class PlanetMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ planet }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PlanetMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.planet = planet;
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
