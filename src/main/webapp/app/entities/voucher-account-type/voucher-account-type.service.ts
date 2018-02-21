import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VoucherAccountType } from './voucher-account-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VoucherAccountType>;

@Injectable()
export class VoucherAccountTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/voucher-account-types';

    constructor(private http: HttpClient) { }

    create(voucherAccountType: VoucherAccountType): Observable<EntityResponseType> {
        const copy = this.convert(voucherAccountType);
        return this.http.post<VoucherAccountType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(voucherAccountType: VoucherAccountType): Observable<EntityResponseType> {
        const copy = this.convert(voucherAccountType);
        return this.http.put<VoucherAccountType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VoucherAccountType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VoucherAccountType[]>> {
        const options = createRequestOption(req);
        return this.http.get<VoucherAccountType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VoucherAccountType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VoucherAccountType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VoucherAccountType[]>): HttpResponse<VoucherAccountType[]> {
        const jsonResponse: VoucherAccountType[] = res.body;
        const body: VoucherAccountType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VoucherAccountType.
     */
    private convertItemFromServer(voucherAccountType: VoucherAccountType): VoucherAccountType {
        const copy: VoucherAccountType = Object.assign({}, voucherAccountType);
        return copy;
    }

    /**
     * Convert a VoucherAccountType to a JSON which can be sent to the server.
     */
    private convert(voucherAccountType: VoucherAccountType): VoucherAccountType {
        const copy: VoucherAccountType = Object.assign({}, voucherAccountType);
        return copy;
    }
}
