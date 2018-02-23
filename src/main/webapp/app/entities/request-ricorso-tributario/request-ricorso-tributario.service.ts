import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestRicorsoTributario>;

@Injectable()
export class RequestRicorsoTributarioService {

    private resourceUrl =  SERVER_API_URL + 'api/request-ricorso-tributarios';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(requestRicorsoTributario: RequestRicorsoTributario): Observable<EntityResponseType> {
        const copy = this.convert(requestRicorsoTributario);
        return this.http.post<RequestRicorsoTributario>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(requestRicorsoTributario: RequestRicorsoTributario): Observable<EntityResponseType> {
        const copy = this.convert(requestRicorsoTributario);
        return this.http.put<RequestRicorsoTributario>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestRicorsoTributario>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestRicorsoTributario[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestRicorsoTributario[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestRicorsoTributario[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestRicorsoTributario = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestRicorsoTributario[]>): HttpResponse<RequestRicorsoTributario[]> {
        const jsonResponse: RequestRicorsoTributario[] = res.body;
        const body: RequestRicorsoTributario[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestRicorsoTributario.
     */
    private convertItemFromServer(requestRicorsoTributario: RequestRicorsoTributario): RequestRicorsoTributario {
        const copy: RequestRicorsoTributario = Object.assign({}, requestRicorsoTributario);
        copy.notificaDate = this.dateUtils
            .convertDateTimeFromServer(requestRicorsoTributario.notificaDate);
        copy.emissioneRuoloDate = this.dateUtils
            .convertDateTimeFromServer(requestRicorsoTributario.emissioneRuoloDate);
        return copy;
    }

    /**
     * Convert a RequestRicorsoTributario to a JSON which can be sent to the server.
     */
    private convert(requestRicorsoTributario: RequestRicorsoTributario): RequestRicorsoTributario {
        const copy: RequestRicorsoTributario = Object.assign({}, requestRicorsoTributario);

        copy.notificaDate = this.dateUtils.toDate(requestRicorsoTributario.notificaDate);

        copy.emissioneRuoloDate = this.dateUtils.toDate(requestRicorsoTributario.emissioneRuoloDate);
        return copy;
    }
}
