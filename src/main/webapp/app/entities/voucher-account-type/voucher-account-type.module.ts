import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    VoucherAccountTypeService,
    VoucherAccountTypePopupService,
    VoucherAccountTypeComponent,
    VoucherAccountTypeDetailComponent,
    VoucherAccountTypeDialogComponent,
    VoucherAccountTypePopupComponent,
    VoucherAccountTypeDeletePopupComponent,
    VoucherAccountTypeDeleteDialogComponent,
    voucherAccountTypeRoute,
    voucherAccountTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...voucherAccountTypeRoute,
    ...voucherAccountTypePopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoucherAccountTypeComponent,
        VoucherAccountTypeDetailComponent,
        VoucherAccountTypeDialogComponent,
        VoucherAccountTypeDeleteDialogComponent,
        VoucherAccountTypePopupComponent,
        VoucherAccountTypeDeletePopupComponent,
    ],
    entryComponents: [
        VoucherAccountTypeComponent,
        VoucherAccountTypeDialogComponent,
        VoucherAccountTypePopupComponent,
        VoucherAccountTypeDeleteDialogComponent,
        VoucherAccountTypeDeletePopupComponent,
    ],
    providers: [
        VoucherAccountTypeService,
        VoucherAccountTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerVoucherAccountTypeModule {}
