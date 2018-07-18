/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemForecastMySuffixDetailComponent } from 'app/entities/solar-system-forecast-my-suffix/solar-system-forecast-my-suffix-detail.component';
import { SolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemForecastMySuffix Management Detail Component', () => {
        let comp: SolarSystemForecastMySuffixDetailComponent;
        let fixture: ComponentFixture<SolarSystemForecastMySuffixDetailComponent>;
        const route = ({ data: of({ solarSystemForecast: new SolarSystemForecastMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemForecastMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SolarSystemForecastMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SolarSystemForecastMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.solarSystemForecast).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
