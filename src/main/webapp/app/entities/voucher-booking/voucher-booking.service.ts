import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { VoucherBooking } from './voucher-booking.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<VoucherBooking>;

@Injectable()
export class VoucherBookingService {

    private resourceUrl =  SERVER_API_URL + 'api/voucher-bookings';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(voucherBooking: VoucherBooking): Observable<EntityResponseType> {
        const copy = this.convert(voucherBooking);
        return this.http.post<VoucherBooking>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(voucherBooking: VoucherBooking): Observable<EntityResponseType> {
        const copy = this.convert(voucherBooking);
        return this.http.put<VoucherBooking>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<VoucherBooking>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<VoucherBooking[]>> {
        const options = createRequestOption(req);
        return this.http.get<VoucherBooking[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<VoucherBooking[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: VoucherBooking = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<VoucherBooking[]>): HttpResponse<VoucherBooking[]> {
        const jsonResponse: VoucherBooking[] = res.body;
        const body: VoucherBooking[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to VoucherBooking.
     */
    private convertItemFromServer(voucherBooking: VoucherBooking): VoucherBooking {
        const copy: VoucherBooking = Object.assign({}, voucherBooking);
        copy.doneDate = this.dateUtils
            .convertLocalDateFromServer(voucherBooking.doneDate);
        copy.valueDate = this.dateUtils
            .convertLocalDateFromServer(voucherBooking.valueDate);
        copy.transactionDate = this.dateUtils
            .convertLocalDateFromServer(voucherBooking.transactionDate);
        return copy;
    }

    /**
     * Convert a VoucherBooking to a JSON which can be sent to the server.
     */
    private convert(voucherBooking: VoucherBooking): VoucherBooking {
        const copy: VoucherBooking = Object.assign({}, voucherBooking);
        copy.doneDate = this.dateUtils
            .convertLocalDateToServer(voucherBooking.doneDate);
        copy.valueDate = this.dateUtils
            .convertLocalDateToServer(voucherBooking.valueDate);
        copy.transactionDate = this.dateUtils
            .convertLocalDateToServer(voucherBooking.transactionDate);
        return copy;
    }
}
