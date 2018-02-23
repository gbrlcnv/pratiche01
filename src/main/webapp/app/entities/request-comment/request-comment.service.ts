import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { RequestComment } from './request-comment.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<RequestComment>;

@Injectable()
export class RequestCommentService {

    private resourceUrl =  SERVER_API_URL + 'api/request-comments';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(requestComment: RequestComment): Observable<EntityResponseType> {
        const copy = this.convert(requestComment);
        return this.http.post<RequestComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(requestComment: RequestComment): Observable<EntityResponseType> {
        const copy = this.convert(requestComment);
        return this.http.put<RequestComment>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<RequestComment>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<RequestComment[]>> {
        const options = createRequestOption(req);
        return this.http.get<RequestComment[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<RequestComment[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: RequestComment = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<RequestComment[]>): HttpResponse<RequestComment[]> {
        const jsonResponse: RequestComment[] = res.body;
        const body: RequestComment[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to RequestComment.
     */
    private convertItemFromServer(requestComment: RequestComment): RequestComment {
        const copy: RequestComment = Object.assign({}, requestComment);
        copy.commentDate = this.dateUtils
            .convertDateTimeFromServer(requestComment.commentDate);
        return copy;
    }

    /**
     * Convert a RequestComment to a JSON which can be sent to the server.
     */
    private convert(requestComment: RequestComment): RequestComment {
        const copy: RequestComment = Object.assign({}, requestComment);

        copy.commentDate = this.dateUtils.toDate(requestComment.commentDate);
        return copy;
    }
}
