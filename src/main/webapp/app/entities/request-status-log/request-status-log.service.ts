import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RequestStatusLog } from './request-status-log.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestStatusLog>;

@Injectable()
export class RequestStatusLogService {

    private resourceUrl =  SERVER_API_URL + 'api/request-status-logs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(requestStatusLog: RequestStatusLog): Observable<EntityResponseType> {
        const copy = this.convert(requestStatusLog);
        return this.http.post<RequestStatusLog>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(requestStatusLog: RequestStatusLog): Observable<EntityResponseType> {
        const copy = this.convert(requestStatusLog);
        return this.http.put<RequestStatusLog>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestStatusLog>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestStatusLog[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestStatusLog[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestStatusLog[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestStatusLog = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestStatusLog[]>): HttpResponse<RequestStatusLog[]> {
        const jsonResponse: RequestStatusLog[] = res.body;
        const body: RequestStatusLog[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestStatusLog.
     */
    private convertItemFromServer(requestStatusLog: RequestStatusLog): RequestStatusLog {
        const copy: RequestStatusLog = Object.assign({}, requestStatusLog);
        copy.statusFromDate = this.dateUtils
            .convertDateTimeFromServer(requestStatusLog.statusFromDate);
        copy.statusChangeDate = this.dateUtils
            .convertDateTimeFromServer(requestStatusLog.statusChangeDate);
        return copy;
    }

    /**
     * Convert a RequestStatusLog to a JSON which can be sent to the server.
     */
    private convert(requestStatusLog: RequestStatusLog): RequestStatusLog {
        const copy: RequestStatusLog = Object.assign({}, requestStatusLog);

        copy.statusFromDate = this.dateUtils.toDate(requestStatusLog.statusFromDate);

        copy.statusChangeDate = this.dateUtils.toDate(requestStatusLog.statusChangeDate);
        return copy;
    }
}
