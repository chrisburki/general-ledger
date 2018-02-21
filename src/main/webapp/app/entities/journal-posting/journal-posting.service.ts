import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { JournalPosting } from './journal-posting.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<JournalPosting>;

@Injectable()
export class JournalPostingService {

    private resourceUrl =  SERVER_API_URL + 'api/journal-postings';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(journalPosting: JournalPosting): Observable<EntityResponseType> {
        const copy = this.convert(journalPosting);
        return this.http.post<JournalPosting>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(journalPosting: JournalPosting): Observable<EntityResponseType> {
        const copy = this.convert(journalPosting);
        return this.http.put<JournalPosting>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<JournalPosting>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<JournalPosting[]>> {
        const options = createRequestOption(req);
        return this.http.get<JournalPosting[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<JournalPosting[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: JournalPosting = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<JournalPosting[]>): HttpResponse<JournalPosting[]> {
        const jsonResponse: JournalPosting[] = res.body;
        const body: JournalPosting[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to JournalPosting.
     */
    private convertItemFromServer(journalPosting: JournalPosting): JournalPosting {
        const copy: JournalPosting = Object.assign({}, journalPosting);
        copy.bookDate = this.dateUtils
            .convertLocalDateFromServer(journalPosting.bookDate);
        return copy;
    }

    /**
     * Convert a JournalPosting to a JSON which can be sent to the server.
     */
    private convert(journalPosting: JournalPosting): JournalPosting {
        const copy: JournalPosting = Object.assign({}, journalPosting);
        copy.bookDate = this.dateUtils
            .convertLocalDateToServer(journalPosting.bookDate);
        return copy;
    }
}
