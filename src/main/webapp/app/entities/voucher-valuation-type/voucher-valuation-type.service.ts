import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VoucherValuationType } from './voucher-valuation-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VoucherValuationType>;

@Injectable()
export class VoucherValuationTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/voucher-valuation-types';

    constructor(private http: HttpClient) { }

    create(voucherValuationType: VoucherValuationType): Observable<EntityResponseType> {
        const copy = this.convert(voucherValuationType);
        return this.http.post<VoucherValuationType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(voucherValuationType: VoucherValuationType): Observable<EntityResponseType> {
        const copy = this.convert(voucherValuationType);
        return this.http.put<VoucherValuationType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VoucherValuationType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VoucherValuationType[]>> {
        const options = createRequestOption(req);
        return this.http.get<VoucherValuationType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VoucherValuationType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VoucherValuationType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VoucherValuationType[]>): HttpResponse<VoucherValuationType[]> {
        const jsonResponse: VoucherValuationType[] = res.body;
        const body: VoucherValuationType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VoucherValuationType.
     */
    private convertItemFromServer(voucherValuationType: VoucherValuationType): VoucherValuationType {
        const copy: VoucherValuationType = Object.assign({}, voucherValuationType);
        return copy;
    }

    /**
     * Convert a VoucherValuationType to a JSON which can be sent to the server.
     */
    private convert(voucherValuationType: VoucherValuationType): VoucherValuationType {
        const copy: VoucherValuationType = Object.assign({}, voucherValuationType);
        return copy;
    }
}
