import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';
import { PlanetMySuffixService } from './planet-my-suffix.service';
import { PlanetMySuffixComponent } from './planet-my-suffix.component';
import { PlanetMySuffixDetailComponent } from './planet-my-suffix-detail.component';
import { PlanetMySuffixUpdateComponent } from './planet-my-suffix-update.component';
import { PlanetMySuffixDeletePopupComponent } from './planet-my-suffix-delete-dialog.component';
import { IPlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class PlanetMySuffixResolve implements Resolve<IPlanetMySuffix> {
    constructor(private service: PlanetMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((planet: HttpResponse<PlanetMySuffix>) => planet.body));
        }
        return of(new PlanetMySuffix());
    }
}

export const planetRoute: Routes = [
    {
        path: 'planet-my-suffix',
        component: PlanetMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.planet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'planet-my-suffix/:id/view',
        component: PlanetMySuffixDetailComponent,
        resolve: {
            planet: PlanetMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.planet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'planet-my-suffix/new',
        component: PlanetMySuffixUpdateComponent,
        resolve: {
            planet: PlanetMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.planet.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'planet-my-suffix/:id/edit',
        component: PlanetMySuffixUpdateComponent,
        resolve: {
            planet: PlanetMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.planet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const planetPopupRoute: Routes = [
    {
        path: 'planet-my-suffix/:id/delete',
        component: PlanetMySuffixDeletePopupComponent,
        resolve: {
            planet: PlanetMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.planet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
