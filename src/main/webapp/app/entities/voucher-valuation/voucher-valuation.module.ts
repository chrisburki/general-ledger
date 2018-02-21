import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    VoucherValuationService,
    VoucherValuationPopupService,
    VoucherValuationComponent,
    VoucherValuationDetailComponent,
    VoucherValuationDialogComponent,
    VoucherValuationPopupComponent,
    VoucherValuationDeletePopupComponent,
    VoucherValuationDeleteDialogComponent,
    voucherValuationRoute,
    voucherValuationPopupRoute,
    VoucherValuationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...voucherValuationRoute,
    ...voucherValuationPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoucherValuationComponent,
        VoucherValuationDetailComponent,
        VoucherValuationDialogComponent,
        VoucherValuationDeleteDialogComponent,
        VoucherValuationPopupComponent,
        VoucherValuationDeletePopupComponent,
    ],
    entryComponents: [
        VoucherValuationComponent,
        VoucherValuationDialogComponent,
        VoucherValuationPopupComponent,
        VoucherValuationDeleteDialogComponent,
        VoucherValuationDeletePopupComponent,
    ],
    providers: [
        VoucherValuationService,
        VoucherValuationPopupService,
        VoucherValuationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerVoucherValuationModule {}
