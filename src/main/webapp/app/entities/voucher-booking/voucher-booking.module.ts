import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    VoucherBookingService,
    VoucherBookingPopupService,
    VoucherBookingComponent,
    VoucherBookingDetailComponent,
    VoucherBookingDialogComponent,
    VoucherBookingPopupComponent,
    VoucherBookingDeletePopupComponent,
    VoucherBookingDeleteDialogComponent,
    voucherBookingRoute,
    voucherBookingPopupRoute,
    VoucherBookingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...voucherBookingRoute,
    ...voucherBookingPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoucherBookingComponent,
        VoucherBookingDetailComponent,
        VoucherBookingDialogComponent,
        VoucherBookingDeleteDialogComponent,
        VoucherBookingPopupComponent,
        VoucherBookingDeletePopupComponent,
    ],
    entryComponents: [
        VoucherBookingComponent,
        VoucherBookingDialogComponent,
        VoucherBookingPopupComponent,
        VoucherBookingDeleteDialogComponent,
        VoucherBookingDeletePopupComponent,
    ],
    providers: [
        VoucherBookingService,
        VoucherBookingPopupService,
        VoucherBookingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerVoucherBookingModule {}
