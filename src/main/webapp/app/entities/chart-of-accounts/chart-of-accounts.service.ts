import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ChartOfAccounts } from './chart-of-accounts.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ChartOfAccounts>;

@Injectable()
export class ChartOfAccountsService {

    private resourceUrl =  SERVER_API_URL + 'api/chart-of-accounts';

    constructor(private http: HttpClient) { }

    create(chartOfAccounts: ChartOfAccounts): Observable<EntityResponseType> {
        const copy = this.convert(chartOfAccounts);
        return this.http.post<ChartOfAccounts>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(chartOfAccounts: ChartOfAccounts): Observable<EntityResponseType> {
        const copy = this.convert(chartOfAccounts);
        return this.http.put<ChartOfAccounts>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ChartOfAccounts>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ChartOfAccounts[]>> {
        const options = createRequestOption(req);
        return this.http.get<ChartOfAccounts[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ChartOfAccounts[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ChartOfAccounts = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ChartOfAccounts[]>): HttpResponse<ChartOfAccounts[]> {
        const jsonResponse: ChartOfAccounts[] = res.body;
        const body: ChartOfAccounts[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ChartOfAccounts.
     */
    private convertItemFromServer(chartOfAccounts: ChartOfAccounts): ChartOfAccounts {
        const copy: ChartOfAccounts = Object.assign({}, chartOfAccounts);
        return copy;
    }

    /**
     * Convert a ChartOfAccounts to a JSON which can be sent to the server.
     */
    private convert(chartOfAccounts: ChartOfAccounts): ChartOfAccounts {
        const copy: ChartOfAccounts = Object.assign({}, chartOfAccounts);
        return copy;
    }
}
