import { BaseEntity } from './../../shared';

export class BalanceSheetItem implements BaseEntity {
    constructor(
        public id?: number,
        public balanceDate?: any,
        public amount?: number,
        public deltaAmountDebit?: number,
        public deltaAmountCredit?: number,
        public currencyIso?: string,
        public amountCurrency?: number,
        public deltaAmountDebitCurrency?: number,
        public deltaAmountCreditCurrency?: number,
        public isFinal?: boolean,
        public legalEntityId?: string,
        public balanceSheetId?: number,
        public accountId?: number,
    ) {
        this.isFinal = false;
    }
}
