/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemForecastMySuffixComponent } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix.component';
import { SolarSystemForecastMySuffixService } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix.service';
import { SolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemForecastMySuffix Management Component', () => {
        let comp: SolarSystemForecastMySuffixComponent;
        let fixture: ComponentFixture<SolarSystemForecastMySuffixComponent>;
        let service: SolarSystemForecastMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemForecastMySuffixComponent],
                providers: []
            })
                .overrideTemplate(SolarSystemForecastMySuffixComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SolarSystemForecastMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemForecastMySuffixService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SolarSystemForecastMySuffix(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.solarSystemForecasts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
