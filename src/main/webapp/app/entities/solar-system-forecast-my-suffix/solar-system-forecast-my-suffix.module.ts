import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SolarSystemSharedModule } from 'app/shared';
import {
    SolarSystemForecastMySuffixComponent,
    SolarSystemForecastMySuffixDetailComponent,
    SolarSystemForecastMySuffixUpdateComponent,
    SolarSystemForecastMySuffixDeletePopupComponent,
    SolarSystemForecastMySuffixDeleteDialogComponent,
    solarSystemForecastRoute,
    solarSystemForecastPopupRoute
} from './';

const ENTITY_STATES = [...solarSystemForecastRoute, ...solarSystemForecastPopupRoute];

@NgModule({
    imports: [SolarSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SolarSystemForecastMySuffixComponent,
        SolarSystemForecastMySuffixDetailComponent,
        SolarSystemForecastMySuffixUpdateComponent,
        SolarSystemForecastMySuffixDeleteDialogComponent,
        SolarSystemForecastMySuffixDeletePopupComponent
    ],
    entryComponents: [
        SolarSystemForecastMySuffixComponent,
        SolarSystemForecastMySuffixUpdateComponent,
        SolarSystemForecastMySuffixDeleteDialogComponent,
        SolarSystemForecastMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SolarSystemSolarSystemForecastMySuffixModule {}
