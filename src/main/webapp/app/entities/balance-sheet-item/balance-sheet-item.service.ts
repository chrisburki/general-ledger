import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BalanceSheetItem } from './balance-sheet-item.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BalanceSheetItem>;

@Injectable()
export class BalanceSheetItemService {

    private resourceUrl =  SERVER_API_URL + 'api/balance-sheet-items';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(balanceSheetItem: BalanceSheetItem): Observable<EntityResponseType> {
        const copy = this.convert(balanceSheetItem);
        return this.http.post<BalanceSheetItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(balanceSheetItem: BalanceSheetItem): Observable<EntityResponseType> {
        const copy = this.convert(balanceSheetItem);
        return this.http.put<BalanceSheetItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BalanceSheetItem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BalanceSheetItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<BalanceSheetItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BalanceSheetItem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BalanceSheetItem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BalanceSheetItem[]>): HttpResponse<BalanceSheetItem[]> {
        const jsonResponse: BalanceSheetItem[] = res.body;
        const body: BalanceSheetItem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BalanceSheetItem.
     */
    private convertItemFromServer(balanceSheetItem: BalanceSheetItem): BalanceSheetItem {
        const copy: BalanceSheetItem = Object.assign({}, balanceSheetItem);
        copy.balanceDate = this.dateUtils
            .convertLocalDateFromServer(balanceSheetItem.balanceDate);
        return copy;
    }

    /**
     * Convert a BalanceSheetItem to a JSON which can be sent to the server.
     */
    private convert(balanceSheetItem: BalanceSheetItem): BalanceSheetItem {
        const copy: BalanceSheetItem = Object.assign({}, balanceSheetItem);
        copy.balanceDate = this.dateUtils
            .convertLocalDateToServer(balanceSheetItem.balanceDate);
        return copy;
    }
}
