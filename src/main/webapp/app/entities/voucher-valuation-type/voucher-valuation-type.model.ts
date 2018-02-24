import { BaseEntity } from './../../shared';

export class VoucherValuationType implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public name?: string,
    ) {
    }
}
