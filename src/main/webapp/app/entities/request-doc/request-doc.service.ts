import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RequestDoc } from './request-doc.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestDoc>;

@Injectable()
export class RequestDocService {

    private resourceUrl =  SERVER_API_URL + 'api/request-docs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(requestDoc: RequestDoc): Observable<EntityResponseType> {
        const copy = this.convert(requestDoc);
        return this.http.post<RequestDoc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(requestDoc: RequestDoc): Observable<EntityResponseType> {
        const copy = this.convert(requestDoc);
        return this.http.put<RequestDoc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestDoc>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestDoc[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestDoc[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestDoc[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestDoc = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestDoc[]>): HttpResponse<RequestDoc[]> {
        const jsonResponse: RequestDoc[] = res.body;
        const body: RequestDoc[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestDoc.
     */
    private convertItemFromServer(requestDoc: RequestDoc): RequestDoc {
        const copy: RequestDoc = Object.assign({}, requestDoc);
        copy.submissionDate = this.dateUtils
            .convertDateTimeFromServer(requestDoc.submissionDate);
        copy.updateDate = this.dateUtils
            .convertDateTimeFromServer(requestDoc.updateDate);
        return copy;
    }

    /**
     * Convert a RequestDoc to a JSON which can be sent to the server.
     */
    private convert(requestDoc: RequestDoc): RequestDoc {
        const copy: RequestDoc = Object.assign({}, requestDoc);

        copy.submissionDate = this.dateUtils.toDate(requestDoc.submissionDate);

        copy.updateDate = this.dateUtils.toDate(requestDoc.updateDate);
        return copy;
    }
}
