import { BaseEntity } from './../../shared';

export class FinancialInstrumentType implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public name?: string,
    ) {
    }
}
