import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BalanceSheet } from './balance-sheet.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BalanceSheet>;

@Injectable()
export class BalanceSheetService {

    private resourceUrl =  SERVER_API_URL + 'api/balance-sheets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(balanceSheet: BalanceSheet): Observable<EntityResponseType> {
        const copy = this.convert(balanceSheet);
        return this.http.post<BalanceSheet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(balanceSheet: BalanceSheet): Observable<EntityResponseType> {
        const copy = this.convert(balanceSheet);
        return this.http.put<BalanceSheet>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BalanceSheet>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BalanceSheet[]>> {
        const options = createRequestOption(req);
        return this.http.get<BalanceSheet[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BalanceSheet[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BalanceSheet = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BalanceSheet[]>): HttpResponse<BalanceSheet[]> {
        const jsonResponse: BalanceSheet[] = res.body;
        const body: BalanceSheet[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BalanceSheet.
     */
    private convertItemFromServer(balanceSheet: BalanceSheet): BalanceSheet {
        const copy: BalanceSheet = Object.assign({}, balanceSheet);
        copy.balanceDate = this.dateUtils
            .convertLocalDateFromServer(balanceSheet.balanceDate);
        return copy;
    }

    /**
     * Convert a BalanceSheet to a JSON which can be sent to the server.
     */
    private convert(balanceSheet: BalanceSheet): BalanceSheet {
        const copy: BalanceSheet = Object.assign({}, balanceSheet);
        copy.balanceDate = this.dateUtils
            .convertLocalDateToServer(balanceSheet.balanceDate);
        return copy;
    }
}
