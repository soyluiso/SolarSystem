/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemForecastMySuffixUpdateComponent } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix-update.component';
import { SolarSystemForecastMySuffixService } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix.service';
import { SolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemForecastMySuffix Management Update Component', () => {
        let comp: SolarSystemForecastMySuffixUpdateComponent;
        let fixture: ComponentFixture<SolarSystemForecastMySuffixUpdateComponent>;
        let service: SolarSystemForecastMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemForecastMySuffixUpdateComponent]
            })
                .overrideTemplate(SolarSystemForecastMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SolarSystemForecastMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemForecastMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SolarSystemForecastMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.solarSystemForecast = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SolarSystemForecastMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.solarSystemForecast = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
