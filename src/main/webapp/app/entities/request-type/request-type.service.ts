import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { RequestType } from './request-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestType>;

@Injectable()
export class RequestTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/request-types';

    constructor(private http: HttpClient) { }

    create(requestType: RequestType): Observable<EntityResponseType> {
        const copy = this.convert(requestType);
        return this.http.post<RequestType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(requestType: RequestType): Observable<EntityResponseType> {
        const copy = this.convert(requestType);
        return this.http.put<RequestType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestType[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestType[]>): HttpResponse<RequestType[]> {
        const jsonResponse: RequestType[] = res.body;
        const body: RequestType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestType.
     */
    private convertItemFromServer(requestType: RequestType): RequestType {
        const copy: RequestType = Object.assign({}, requestType);
        return copy;
    }

    /**
     * Convert a RequestType to a JSON which can be sent to the server.
     */
    private convert(requestType: RequestType): RequestType {
        const copy: RequestType = Object.assign({}, requestType);
        return copy;
    }
}
