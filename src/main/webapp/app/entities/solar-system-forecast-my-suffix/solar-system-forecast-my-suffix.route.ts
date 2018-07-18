import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';
import { SolarSystemForecastMySuffixService } from './solar-system-forecast-my-suffix.service';
import { SolarSystemForecastMySuffixComponent } from './solar-system-forecast-my-suffix.component';
import { SolarSystemForecastMySuffixDetailComponent } from './solar-system-forecast-my-suffix-detail.component';
import { SolarSystemForecastMySuffixUpdateComponent } from './solar-system-forecast-my-suffix-update.component';
import { SolarSystemForecastMySuffixDeletePopupComponent } from './solar-system-forecast-my-suffix-delete-dialog.component';
import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class SolarSystemForecastMySuffixResolve implements Resolve<ISolarSystemForecastMySuffix> {
    constructor(private service: SolarSystemForecastMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((solarSystemForecast: HttpResponse<SolarSystemForecastMySuffix>) => solarSystemForecast.body));
        }
        return of(new SolarSystemForecastMySuffix());
    }
}

export const solarSystemForecastRoute: Routes = [
    {
        path: 'solar-system-forecast-my-suffix',
        component: SolarSystemForecastMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystemForecast.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-forecast-my-suffix/:id/view',
        component: SolarSystemForecastMySuffixDetailComponent,
        resolve: {
            solarSystemForecast: SolarSystemForecastMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystemForecast.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-forecast-my-suffix/new',
        component: SolarSystemForecastMySuffixUpdateComponent,
        resolve: {
            solarSystemForecast: SolarSystemForecastMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystemForecast.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'solar-system-forecast-my-suffix/:id/edit',
        component: SolarSystemForecastMySuffixUpdateComponent,
        resolve: {
            solarSystemForecast: SolarSystemForecastMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystemForecast.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solarSystemForecastPopupRoute: Routes = [
    {
        path: 'solar-system-forecast-my-suffix/:id/delete',
        component: SolarSystemForecastMySuffixDeletePopupComponent,
        resolve: {
            solarSystemForecast: SolarSystemForecastMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'solarSystemApp.solarSystemForecast.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
