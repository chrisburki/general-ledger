import { BaseEntity } from './../../shared';

export class JournalPosting implements BaseEntity {
    constructor(
        public id?: number,
        public bookDate?: any,
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
