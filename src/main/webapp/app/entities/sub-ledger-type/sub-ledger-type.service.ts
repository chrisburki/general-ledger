import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SubLedgerType } from './sub-ledger-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SubLedgerType>;

@Injectable()
export class SubLedgerTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/sub-ledger-types';

    constructor(private http: HttpClient) { }

    create(subLedgerType: SubLedgerType): Observable<EntityResponseType> {
        const copy = this.convert(subLedgerType);
        return this.http.post<SubLedgerType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(subLedgerType: SubLedgerType): Observable<EntityResponseType> {
        const copy = this.convert(subLedgerType);
        return this.http.put<SubLedgerType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SubLedgerType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SubLedgerType[]>> {
        const options = createRequestOption(req);
        return this.http.get<SubLedgerType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SubLedgerType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SubLedgerType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SubLedgerType[]>): HttpResponse<SubLedgerType[]> {
        const jsonResponse: SubLedgerType[] = res.body;
        const body: SubLedgerType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SubLedgerType.
     */
    private convertItemFromServer(subLedgerType: SubLedgerType): SubLedgerType {
        const copy: SubLedgerType = Object.assign({}, subLedgerType);
        return copy;
    }

    /**
     * Convert a SubLedgerType to a JSON which can be sent to the server.
     */
    private convert(subLedgerType: SubLedgerType): SubLedgerType {
        const copy: SubLedgerType = Object.assign({}, subLedgerType);
        return copy;
    }
}
