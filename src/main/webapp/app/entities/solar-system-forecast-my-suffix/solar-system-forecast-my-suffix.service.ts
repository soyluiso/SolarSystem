import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISolarSystemForecastMySuffix } from 'app/shared/model/solar-system-forecast-my-suffix.model';

type EntityResponseType = HttpResponse<ISolarSystemForecastMySuffix>;
type EntityArrayResponseType = HttpResponse<ISolarSystemForecastMySuffix[]>;

@Injectable({ providedIn: 'root' })
export class SolarSystemForecastMySuffixService {
    private resourceUrl = SERVER_API_URL + 'api/solar-system-forecasts';

    constructor(private http: HttpClient) {}

    create(solarSystemForecast: ISolarSystemForecastMySuffix): Observable<EntityResponseType> {
        return this.http.post<ISolarSystemForecastMySuffix>(this.resourceUrl, solarSystemForecast, { observe: 'response' });
    }

    update(solarSystemForecast: ISolarSystemForecastMySuffix): Observable<EntityResponseType> {
        return this.http.put<ISolarSystemForecastMySuffix>(this.resourceUrl, solarSystemForecast, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISolarSystemForecastMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISolarSystemForecastMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
