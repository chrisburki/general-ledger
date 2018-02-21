import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FinancialInstrumentType } from './financial-instrument-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FinancialInstrumentType>;

@Injectable()
export class FinancialInstrumentTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/financial-instrument-types';

    constructor(private http: HttpClient) { }

    create(financialInstrumentType: FinancialInstrumentType): Observable<EntityResponseType> {
        const copy = this.convert(financialInstrumentType);
        return this.http.post<FinancialInstrumentType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(financialInstrumentType: FinancialInstrumentType): Observable<EntityResponseType> {
        const copy = this.convert(financialInstrumentType);
        return this.http.put<FinancialInstrumentType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FinancialInstrumentType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FinancialInstrumentType[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinancialInstrumentType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinancialInstrumentType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FinancialInstrumentType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FinancialInstrumentType[]>): HttpResponse<FinancialInstrumentType[]> {
        const jsonResponse: FinancialInstrumentType[] = res.body;
        const body: FinancialInstrumentType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FinancialInstrumentType.
     */
    private convertItemFromServer(financialInstrumentType: FinancialInstrumentType): FinancialInstrumentType {
        const copy: FinancialInstrumentType = Object.assign({}, financialInstrumentType);
        return copy;
    }

    /**
     * Convert a FinancialInstrumentType to a JSON which can be sent to the server.
     */
    private convert(financialInstrumentType: FinancialInstrumentType): FinancialInstrumentType {
        const copy: FinancialInstrumentType = Object.assign({}, financialInstrumentType);
        return copy;
    }
}
