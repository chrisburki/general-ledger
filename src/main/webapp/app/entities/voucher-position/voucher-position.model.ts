import { BaseEntity } from './../../shared';

export class VoucherPosition implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public key?: string,
        public currencyIso?: string,
        public positionId?: string,
        public positionKeepingId?: string,
        public legalEntityId?: string,
        public financialInstrumentTypeId?: number,
        public accountTypeId?: number,
        public subLedgerTypeId?: number,
    ) {
    }
}
