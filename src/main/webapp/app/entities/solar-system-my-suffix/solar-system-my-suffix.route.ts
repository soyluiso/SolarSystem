import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';
import { SolarSystemMySuffixService } from './solar-system-my-suffix.service';
import { SolarSystemMySuffixComponent } from './solar-system-my-suffix.component';
import { SolarSystemMySuffixDetailComponent } from './solar-system-my-suffix-detail.component';
import { SolarSystemMySuffixUpdateComponent } from './solar-system-my-suffix-update.component';
import { SolarSystemMySuffixDeletePopupComponent } from './solar-system-my-suffix-delete-dialog.component';
import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class SolarSystemMySuffixResolve implements Resolve<ISolarSystemMySuffix> {
    constructor(private service: SolarSystemMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((solarSystem: HttpResponse<SolarSystemMySuffix>) => solarSystem.body));
        }
        return of(new SolarSystemMySuffix());
    }
}

export const solarSystemRoute: Routes = [
    {
        path: 'solar-system-my-suffix',
        component: SolarSystemMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-my-suffix/:id/view',
        component: SolarSystemMySuffixDetailComponent,
        resolve: {
            solarSystem: SolarSystemMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-my-suffix/new',
        component: SolarSystemMySuffixUpdateComponent,
        resolve: {
            solarSystem: SolarSystemMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-my-suffix/:id/edit',
        component: SolarSystemMySuffixUpdateComponent,
        resolve: {
            solarSystem: SolarSystemMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solarSystemPopupRoute: Routes = [
    {
        path: 'solar-system-my-suffix/:id/delete',
        component: SolarSystemMySuffixDeletePopupComponent,
        resolve: {
            solarSystem: SolarSystemMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
