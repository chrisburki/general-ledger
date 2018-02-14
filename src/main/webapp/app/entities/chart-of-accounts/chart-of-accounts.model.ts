import { BaseEntity } from './../../shared';

export const enum AccountingStandard {
    'IFRS',
    'HGB',
    'ACUDBU',
    'RRVEBK'
}

export class ChartOfAccounts implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public key?: string,
        public accountingStandard?: AccountingStandard,
        public isMain?: boolean,
        public legalEntityId?: string,
        public positionKeepingId?: string,
    ) {
        this.isMain = false;
    }
}
