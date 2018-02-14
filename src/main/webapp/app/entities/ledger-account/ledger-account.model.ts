import { BaseEntity } from './../../shared';

export const enum LedgerAccountType {
    'ASSETS',
    'LIABILITIES',
    'INCOME',
    'EXPENSE',
    'OFFBALANCE'
}

export class LedgerAccount implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public key?: string,
        public accountType?: LedgerAccountType,
        public orderedBy?: string,
        public level?: number,
        public isleaf?: boolean,
        public balanceAccountId?: string,
        public chartOfAccounts?: BaseEntity,
        public upperAccount?: BaseEntity,
    ) {
        this.isleaf = false;
    }
}
