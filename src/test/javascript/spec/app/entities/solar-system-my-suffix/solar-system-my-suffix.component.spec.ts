/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SolarSystemTestModule } from '../../../test.module';
import { SolarSystemMySuffixComponent } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix.component';
import { SolarSystemMySuffixService } from 'app/entities/solar-system-my-suffix/solar-system-my-suffix.service';
import { SolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

describe('Component Tests', () => {
    describe('SolarSystemMySuffix Management Component', () => {
        let comp: SolarSystemMySuffixComponent;
        let fixture: ComponentFixture<SolarSystemMySuffixComponent>;
        let service: SolarSystemMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SolarSystemTestModule],
                declarations: [SolarSystemMySuffixComponent],
                providers: []
            })
                .overrideTemplate(SolarSystemMySuffixComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SolarSystemMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolarSystemMySuffixService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SolarSystemMySuffix(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.solarSystems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
