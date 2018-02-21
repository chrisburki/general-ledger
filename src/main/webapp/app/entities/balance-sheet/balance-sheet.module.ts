import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    BalanceSheetService,
    BalanceSheetPopupService,
    BalanceSheetComponent,
    BalanceSheetDetailComponent,
    BalanceSheetDialogComponent,
    BalanceSheetPopupComponent,
    BalanceSheetDeletePopupComponent,
    BalanceSheetDeleteDialogComponent,
    balanceSheetRoute,
    balanceSheetPopupRoute,
} from './';

const ENTITY_STATES = [
    ...balanceSheetRoute,
    ...balanceSheetPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BalanceSheetComponent,
        BalanceSheetDetailComponent,
        BalanceSheetDialogComponent,
        BalanceSheetDeleteDialogComponent,
        BalanceSheetPopupComponent,
        BalanceSheetDeletePopupComponent,
    ],
    entryComponents: [
        BalanceSheetComponent,
        BalanceSheetDialogComponent,
        BalanceSheetPopupComponent,
        BalanceSheetDeleteDialogComponent,
        BalanceSheetDeletePopupComponent,
    ],
    providers: [
        BalanceSheetService,
        BalanceSheetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerBalanceSheetModule {}
