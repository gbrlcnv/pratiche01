import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DocStore } from './doc-store.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DocStore>;

@Injectable()
export class DocStoreService {

    private resourceUrl =  SERVER_API_URL + 'api/doc-stores';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(docStore: DocStore): Observable<EntityResponseType> {
        const copy = this.convert(docStore);
        return this.http.post<DocStore>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(docStore: DocStore): Observable<EntityResponseType> {
        const copy = this.convert(docStore);
        return this.http.put<DocStore>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DocStore>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DocStore[]>> {
        const options = createRequestOption(req);
        return this.http.get<DocStore[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DocStore[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DocStore = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DocStore[]>): HttpResponse<DocStore[]> {
        const jsonResponse: DocStore[] = res.body;
        const body: DocStore[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DocStore.
     */
    private convertItemFromServer(docStore: DocStore): DocStore {
        const copy: DocStore = Object.assign({}, docStore);
        copy.creationDate = this.dateUtils
            .convertDateTimeFromServer(docStore.creationDate);
        return copy;
    }

    /**
     * Convert a DocStore to a JSON which can be sent to the server.
     */
    private convert(docStore: DocStore): DocStore {
        const copy: DocStore = Object.assign({}, docStore);

        copy.creationDate = this.dateUtils.toDate(docStore.creationDate);
        return copy;
    }
}
