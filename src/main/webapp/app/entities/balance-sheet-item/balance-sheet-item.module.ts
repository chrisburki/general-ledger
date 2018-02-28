import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    BalanceSheetItemService,
    BalanceSheetItemPopupService,
    BalanceSheetItemComponent,
    BalanceSheetItemDetailComponent,
    BalanceSheetItemDialogComponent,
    BalanceSheetItemPopupComponent,
    BalanceSheetItemDeletePopupComponent,
    BalanceSheetItemDeleteDialogComponent,
    balanceSheetItemRoute,
    balanceSheetItemPopupRoute,
    BalanceSheetItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...balanceSheetItemRoute,
    ...balanceSheetItemPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BalanceSheetItemComponent,
        BalanceSheetItemDetailComponent,
        BalanceSheetItemDialogComponent,
        BalanceSheetItemDeleteDialogComponent,
        BalanceSheetItemPopupComponent,
        BalanceSheetItemDeletePopupComponent,
    ],
    entryComponents: [
        BalanceSheetItemComponent,
        BalanceSheetItemDialogComponent,
        BalanceSheetItemPopupComponent,
        BalanceSheetItemDeleteDialogComponent,
        BalanceSheetItemDeletePopupComponent,
    ],
    providers: [
        BalanceSheetItemService,
        BalanceSheetItemPopupService,
        BalanceSheetItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerBalanceSheetItemModule {}
