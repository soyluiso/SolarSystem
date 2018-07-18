import { IPlanetMySuffix } from 'app/shared/model//planet-my-suffix.model';

export interface ISolarSystemMySuffix {
    id?: number;
    systemName?: string;
    planets?: IPlanetMySuffix[];
}

export class SolarSystemMySuffix implements ISolarSystemMySuffix {
    constructor(public id?: number, public systemName?: string, public planets?: IPlanetMySuffix[]) {}
}
