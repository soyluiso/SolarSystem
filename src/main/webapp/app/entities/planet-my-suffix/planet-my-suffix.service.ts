import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlanetMySuffix } from 'app/shared/model/planet-my-suffix.model';

type EntityResponseType = HttpResponse<IPlanetMySuffix>;
type EntityArrayResponseType = HttpResponse<IPlanetMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class PlanetMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/planets';

    constructor(private http: HttpClient) {}

    create(planet: IPlanetMySuffix): Observable<EntityResponseType> {
        return this.http.post<IPlanetMySuffix>(this.resourceUrl, planet, { observe: 'response' });
    }

    update(planet: IPlanetMySuffix): Observable<EntityResponseType> {
        return this.http.put<IPlanetMySuffix>(this.resourceUrl, planet, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPlanetMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPlanetMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
