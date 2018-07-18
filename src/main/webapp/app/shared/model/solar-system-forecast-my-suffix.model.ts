export interface ISolarSystemForecastMySuffix {
    id?: number;
    day?: number;
    forecast?: string;
    solarSystemId?: number;
}

export class SolarSystemForecastMySuffix implements ISolarSystemForecastMySuffix {
    constructor(public id?: number, public day?: number, public forecast?: string, public solarSystemId?: number) {}
}
