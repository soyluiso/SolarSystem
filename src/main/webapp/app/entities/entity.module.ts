import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SolarSystemPlanetMySuffixModule } from './planet-my-suffix/planet-my-suffix.module';
import { SolarSystemSolarSystemMySuffixModule } from './solar-system-my-suffix/solar-system-my-suffix.module';
import { SolarSystemSolarSystemForecastMySuffixModule } from './solar-system-forecast-my-suffix/solar-system-forecast-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SolarSystemPlanetMySuffixModule,
        SolarSystemSolarSystemMySuffixModule,
        SolarSystemSolarSystemForecastMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SolarSystemEntityModule {}
