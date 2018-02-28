import { BaseEntity } from './../../shared';

export class VoucherBooking implements BaseEntity {
    constructor(
        public id?: number,
        public doneDate?: any,
        public bookDate?: any,
        public valueDate?: any,
        public transactionDate?: any,
        public quantity?: number,
        public amount?: number,
        public currencyIso?: string,
        public amountBaseCurrency?: number,
        public bookingText?: string,
        public globalSequenceNumber?: number,
        public transactionId?: string,
        public eventId?: string,
        public transactionType?: string,
        public businessUseCase?: string,
        public bookingId?: string,
        public positionKeepingId?: string,
        public legalEntityId?: string,
        public journalPostingId?: number,
        public positionId?: number,
    ) {
    }
}
