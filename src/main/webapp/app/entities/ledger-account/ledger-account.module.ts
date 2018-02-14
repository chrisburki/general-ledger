import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    LedgerAccountService,
    LedgerAccountPopupService,
    LedgerAccountComponent,
    LedgerAccountDetailComponent,
    LedgerAccountDialogComponent,
    LedgerAccountPopupComponent,
    LedgerAccountDeletePopupComponent,
    LedgerAccountDeleteDialogComponent,
    ledgerAccountRoute,
    ledgerAccountPopupRoute,
} from './';

const ENTITY_STATES = [
    ...ledgerAccountRoute,
    ...ledgerAccountPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LedgerAccountComponent,
        LedgerAccountDetailComponent,
        LedgerAccountDialogComponent,
        LedgerAccountDeleteDialogComponent,
        LedgerAccountPopupComponent,
        LedgerAccountDeletePopupComponent,
    ],
    entryComponents: [
        LedgerAccountComponent,
        LedgerAccountDialogComponent,
        LedgerAccountPopupComponent,
        LedgerAccountDeleteDialogComponent,
        LedgerAccountDeletePopupComponent,
    ],
    providers: [
        LedgerAccountService,
        LedgerAccountPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerLedgerAccountModule {}
