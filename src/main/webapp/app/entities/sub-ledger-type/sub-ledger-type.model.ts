import { BaseEntity } from './../../shared';

export class SubLedgerType implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public name?: string,
    ) {
    }
}
