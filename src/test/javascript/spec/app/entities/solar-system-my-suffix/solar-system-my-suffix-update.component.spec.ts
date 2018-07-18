/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemMySuffixUpdateComponent } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix-update.component';
import { SolarSystemMySuffixService } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix.service';
import { SolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemMySuffix Management Update Component', () => {
        let comp: SolarSystemMySuffixUpdateComponent;
        let fixture: ComponentFixture<SolarSystemMySuffixUpdateComponent>;
        let service: SolarSystemMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemMySuffixUpdateComponent]
            })
                .overrideTemplate(SolarSystemMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SolarSystemMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SolarSystemMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.solarSystem = entity;
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
                    const entity = new SolarSystemMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.solarSystem = entity;
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
