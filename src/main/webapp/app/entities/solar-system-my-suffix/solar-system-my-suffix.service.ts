import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISolarSystemMySuffix } from 'app/shared/model/solar-system-my-suffix.model';

type EntityResponseType = HttpResponse<ISolarSystemMySuffix>;
type EntityArrayResponseType = HttpResponse<ISolarSystemMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class SolarSystemMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/solar-systems';

    constructor(private http: HttpClient) {}

    create(solarSystem: ISolarSystemMySuffix): Observable<EntityResponseType> {
        return this.http.post<ISolarSystemMySuffix>(this.resourceUrl, solarSystem, { observe: 'response' });
    }

    update(solarSystem: ISolarSystemMySuffix): Observable<EntityResponseType> {
        return this.http.put<ISolarSystemMySuffix>(this.resourceUrl, solarSystem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISolarSystemMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISolarSystemMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
