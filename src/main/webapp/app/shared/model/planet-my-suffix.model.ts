export interface IPlanetMySuffix {
    id?: number;
    planetName?: string;
    planetVelocity?: number;
    planetRadius?: number;
    solarSystemId?: number;
}

export class PlanetMySuffix implements IPlanetMySuffix {
    constructor(
        public id?: number,
        public planetName?: string,
        public planetVelocity?: number,
        public planetRadius?: number,
        public solarSystemId?: number
    ) {}
}
