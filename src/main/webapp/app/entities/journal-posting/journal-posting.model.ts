import { BaseEntity } from './../../shared';

export const enum BalanceDateType {
    'DONE',
    'BOOK',
    'VALUE',
    'TRANSACTION'
}

export class JournalPosting implements BaseEntity {
    constructor(
        public id?: number,
        public bookDate?: any,
        public bookDateType?: BalanceDateType,
        public documentNumber?: string,
        public amount?: number,
        public currencyIso?: string,
        public amountCurrency?: number,
        public bookingText?: string,
        public globalSequenceNumber?: number,
        public legalEntityId?: string,
        public voucherBookings?: BaseEntity[],
        public voucherValuations?: BaseEntity[],
        public debitAccountId?: number,
        public creditAccountId?: number,
    ) {
    }
}
