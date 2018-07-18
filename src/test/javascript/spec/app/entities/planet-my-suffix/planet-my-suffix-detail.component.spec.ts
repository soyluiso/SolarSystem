/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { PlanetMySuffixDetailComponent } from 'app/entities/planet-my-suffix/planet-my-suffix-detail.component';
import { PlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';

describe('Component Tests', () => {
    describe('PlanetMySuffix Management Detail Component', () => {
        let comp: PlanetMySuffixDetailComponent;
        let fixture: ComponentFixture<PlanetMySuffixDetailComponent>;
        const route = ({ data: of({ planet: new PlanetMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [PlanetMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PlanetMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlanetMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.planet).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
