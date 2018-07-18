import { IPlanetMySuffix } from 'app/shared/model//planet-my-suffix.model';
import { ISolarSystemForecastMySuffix } from 'app/shared/model//solar-system-forecast-my-suffix.model';

export interface ISolarSystemMySuffix {
    id?: number;
    systemName?: string;
    planets?: IPlanetMySuffix[];
    forecasts?: ISolarSystemForecastMySuffix[];
}

export class SolarSystemMySuffix implements ISolarSystemMySuffix {
    constructor(
        public id?: number,
        public systemName?: string,
        public planets?: IPlanetMySuffix[],
        public forecasts?: ISolarSystemForecastMySuffix[]
    ) {}
}
