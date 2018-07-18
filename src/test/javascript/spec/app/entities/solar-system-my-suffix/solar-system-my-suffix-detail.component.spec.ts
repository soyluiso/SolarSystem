/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemMySuffixDetailComponent } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix-detail.component';
import { SolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemMySuffix Management Detail Component', () => {
        let comp: SolarSystemMySuffixDetailComponent;
        let fixture: ComponentFixture<SolarSystemMySuffixDetailComponent>;
        const route = ({ data: of({ solarSystem: new SolarSystemMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SolarSystemMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SolarSystemMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.solarSystem).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
