import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VoucherPosition } from './voucher-position.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VoucherPosition>;

@Injectable()
export class VoucherPositionService {

    private resourceUrl =  SERVER_API_URL + 'api/voucher-positions';

    constructor(private http: HttpClient) { }

    create(voucherPosition: VoucherPosition): Observable<EntityResponseType> {
        const copy = this.convert(voucherPosition);
        return this.http.post<VoucherPosition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(voucherPosition: VoucherPosition): Observable<EntityResponseType> {
        const copy = this.convert(voucherPosition);
        return this.http.put<VoucherPosition>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VoucherPosition>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VoucherPosition[]>> {
        const options = createRequestOption(req);
        return this.http.get<VoucherPosition[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VoucherPosition[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VoucherPosition = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VoucherPosition[]>): HttpResponse<VoucherPosition[]> {
        const jsonResponse: VoucherPosition[] = res.body;
        const body: VoucherPosition[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VoucherPosition.
     */
    private convertItemFromServer(voucherPosition: VoucherPosition): VoucherPosition {
        const copy: VoucherPosition = Object.assign({}, voucherPosition);
        return copy;
    }

    /**
     * Convert a VoucherPosition to a JSON which can be sent to the server.
     */
    private convert(voucherPosition: VoucherPosition): VoucherPosition {
        const copy: VoucherPosition = Object.assign({}, voucherPosition);
        return copy;
    }
}
