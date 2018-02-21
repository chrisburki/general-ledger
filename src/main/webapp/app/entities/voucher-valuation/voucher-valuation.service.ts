import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VoucherValuation } from './voucher-valuation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VoucherValuation>;

@Injectable()
export class VoucherValuationService {

    private resourceUrl =  SERVER_API_URL + 'api/voucher-valuations';

    constructor(private http: HttpClient) { }

    create(voucherValuation: VoucherValuation): Observable<EntityResponseType> {
        const copy = this.convert(voucherValuation);
        return this.http.post<VoucherValuation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(voucherValuation: VoucherValuation): Observable<EntityResponseType> {
        const copy = this.convert(voucherValuation);
        return this.http.put<VoucherValuation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VoucherValuation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VoucherValuation[]>> {
        const options = createRequestOption(req);
        return this.http.get<VoucherValuation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VoucherValuation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VoucherValuation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VoucherValuation[]>): HttpResponse<VoucherValuation[]> {
        const jsonResponse: VoucherValuation[] = res.body;
        const body: VoucherValuation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VoucherValuation.
     */
    private convertItemFromServer(voucherValuation: VoucherValuation): VoucherValuation {
        const copy: VoucherValuation = Object.assign({}, voucherValuation);
        return copy;
    }

    /**
     * Convert a VoucherValuation to a JSON which can be sent to the server.
     */
    private convert(voucherValuation: VoucherValuation): VoucherValuation {
        const copy: VoucherValuation = Object.assign({}, voucherValuation);
        return copy;
    }
}
