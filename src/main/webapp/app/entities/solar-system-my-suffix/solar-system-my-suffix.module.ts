import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SolarSystemSharedModule } from 'app/shared';
import {
    SolarSystemMySuffixComponent,
    SolarSystemMySuffixDetailComponent,
    SolarSystemMySuffixUpdateComponent,
    SolarSystemMySuffixDeletePopupComponent,
    SolarSystemMySuffixDeleteDialogComponent,
    solarSystemRoute,
    solarSystemPopupRoute
} from './';

const ENTITY_STATES = [...solarSystemRoute, ...solarSystemPopupRoute];

@NgModule({
    imports: [SolarSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SolarSystemMySuffixComponent,
        SolarSystemMySuffixDetailComponent,
        SolarSystemMySuffixUpdateComponent,
        SolarSystemMySuffixDeleteDialogComponent,
        SolarSystemMySuffixDeletePopupComponent
    ],
    entryComponents: [
        SolarSystemMySuffixComponent,
        SolarSystemMySuffixUpdateComponent,
        SolarSystemMySuffixDeleteDialogComponent,
        SolarSystemMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SolarSystemSolarSystemMySuffixModule {}
