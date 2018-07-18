/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemMySuffixDeleteDialogComponent } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix-delete-dialog.component';
import { SolarSystemMySuffixService } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix.service';

describe('Component Tests', () => {
    describe('SolarSystemMySuffix Management Delete Component', () => {
        let comp: SolarSystemMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<SolarSystemMySuffixDeleteDialogComponent>;
        let service: SolarSystemMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(SolarSystemMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SolarSystemMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemMySuffixService);
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
