import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SolarSystemSharedModule } from 'app/shared';
import {
    PlanetMySuffixComponent,
    PlanetMySuffixDetailComponent,
    PlanetMySuffixUpdateComponent,
    PlanetMySuffixDeletePopupComponent,
    PlanetMySuffixDeleteDialogComponent,
    planetRoute,
    planetPopupRoute
} from './';

const ENTITY_STATES = [...planetRoute, ...planetPopupRoute];

@NgModule({
    imports: [SolarSystemSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PlanetMySuffixComponent,
        PlanetMySuffixDetailComponent,
        PlanetMySuffixUpdateComponent,
        PlanetMySuffixDeleteDialogComponent,
        PlanetMySuffixDeletePopupComponent
    ],
    entryComponents: [
        PlanetMySuffixComponent,
        PlanetMySuffixUpdateComponent,
        PlanetMySuffixDeleteDialogComponent,
        PlanetMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SolarSystemPlanetMySuffixModule {}
