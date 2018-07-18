/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemForecastMySuffixDeleteDialogComponent } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix-delete-dialog.component';
import { SolarSystemForecastMySuffixService } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix.service';

describe('Component Tests', () => {
    describe('SolarSystemForecastMySuffix Management Delete Component', () => {
        let comp: SolarSystemForecastMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<SolarSystemForecastMySuffixDeleteDialogComponent>;
        let service: SolarSystemForecastMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemForecastMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(SolarSystemForecastMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SolarSystemForecastMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemForecastMySuffixService);
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
