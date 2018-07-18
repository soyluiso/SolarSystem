/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SolarSystemTestModule } from '../../../test.module';
import { PlanetMySuffixDeleteDialogComponent } from 'app/entities/planet-my-suffix/planet-my-suffix-delete-dialog.component';
import { PlanetMySuffixService } from 'app/entities/planet-my-suffix/planet-my-suffix.service';

describe('Component Tests', () => {
    describe('PlanetMySuffix Management Delete Component', () => {
        let comp: PlanetMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<PlanetMySuffixDeleteDialogComponent>;
        let service: PlanetMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [PlanetMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(PlanetMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanetMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanetMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
