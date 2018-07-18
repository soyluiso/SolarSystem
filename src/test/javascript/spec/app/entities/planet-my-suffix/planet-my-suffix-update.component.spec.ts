/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { PlanetMySuffixUpdateComponent } from 'app/entities/planet-my-suffix/planet-my-suffix-update.component';
import { PlanetMySuffixService } from 'app/entities/planet-my-suffix/planet-my-suffix.service';
import { PlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';

describe('Component Tests', () => {
    describe('PlanetMySuffix Management Update Component', () => {
        let comp: PlanetMySuffixUpdateComponent;
        let fixture: ComponentFixture<PlanetMySuffixUpdateComponent>;
        let service: PlanetMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [PlanetMySuffixUpdateComponent]
            })
                .overrideTemplate(PlanetMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PlanetMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlanetMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new PlanetMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planet = entity;
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
                    const entity = new PlanetMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.planet = entity;
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
