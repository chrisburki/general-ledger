import { BaseEntity } from './../../shared';

export class VoucherAccountType implements BaseEntity {
    constructor(
        public id?: number,
        public category?: string,
        public name?: string,
    ) {
    }
}
