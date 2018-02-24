import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    VoucherValuationTypeService,
    VoucherValuationTypePopupService,
    VoucherValuationTypeComponent,
    VoucherValuationTypeDetailComponent,
    VoucherValuationTypeDialogComponent,
    VoucherValuationTypePopupComponent,
    VoucherValuationTypeDeletePopupComponent,
    VoucherValuationTypeDeleteDialogComponent,
    voucherValuationTypeRoute,
    voucherValuationTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...voucherValuationTypeRoute,
    ...voucherValuationTypePopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoucherValuationTypeComponent,
        VoucherValuationTypeDetailComponent,
        VoucherValuationTypeDialogComponent,
        VoucherValuationTypeDeleteDialogComponent,
        VoucherValuationTypePopupComponent,
        VoucherValuationTypeDeletePopupComponent,
    ],
    entryComponents: [
        VoucherValuationTypeComponent,
        VoucherValuationTypeDialogComponent,
        VoucherValuationTypePopupComponent,
        VoucherValuationTypeDeleteDialogComponent,
        VoucherValuationTypeDeletePopupComponent,
    ],
    providers: [
        VoucherValuationTypeService,
        VoucherValuationTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerVoucherValuationTypeModule {}
