import { BaseEntity } from './../../shared';

export const enum BalanceDateType {
    'DONE',
    'BOOK',
    'VALUE',
    'TRANSACTION'
}

export class BalanceSheet implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public key?: string,
        public balanceDate?: any,
        public balanceDateType?: BalanceDateType,
        public globalSequenceNumber?: number,
        public legalEntityId?: string,
        public chartOfAccountsId?: number,
    ) {
    }
}
