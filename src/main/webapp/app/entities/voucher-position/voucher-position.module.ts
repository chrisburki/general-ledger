import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    VoucherPositionService,
    VoucherPositionPopupService,
    VoucherPositionComponent,
    VoucherPositionDetailComponent,
    VoucherPositionDialogComponent,
    VoucherPositionPopupComponent,
    VoucherPositionDeletePopupComponent,
    VoucherPositionDeleteDialogComponent,
    voucherPositionRoute,
    voucherPositionPopupRoute,
    VoucherPositionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...voucherPositionRoute,
    ...voucherPositionPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoucherPositionComponent,
        VoucherPositionDetailComponent,
        VoucherPositionDialogComponent,
        VoucherPositionDeleteDialogComponent,
        VoucherPositionPopupComponent,
        VoucherPositionDeletePopupComponent,
    ],
    entryComponents: [
        VoucherPositionComponent,
        VoucherPositionDialogComponent,
        VoucherPositionPopupComponent,
        VoucherPositionDeleteDialogComponent,
        VoucherPositionDeletePopupComponent,
    ],
    providers: [
        VoucherPositionService,
        VoucherPositionPopupService,
        VoucherPositionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerVoucherPositionModule {}
