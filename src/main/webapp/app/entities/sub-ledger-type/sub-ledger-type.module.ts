import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    SubLedgerTypeService,
    SubLedgerTypePopupService,
    SubLedgerTypeComponent,
    SubLedgerTypeDetailComponent,
    SubLedgerTypeDialogComponent,
    SubLedgerTypePopupComponent,
    SubLedgerTypeDeletePopupComponent,
    SubLedgerTypeDeleteDialogComponent,
    subLedgerTypeRoute,
    subLedgerTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...subLedgerTypeRoute,
    ...subLedgerTypePopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SubLedgerTypeComponent,
        SubLedgerTypeDetailComponent,
        SubLedgerTypeDialogComponent,
        SubLedgerTypeDeleteDialogComponent,
        SubLedgerTypePopupComponent,
        SubLedgerTypeDeletePopupComponent,
    ],
    entryComponents: [
        SubLedgerTypeComponent,
        SubLedgerTypeDialogComponent,
        SubLedgerTypePopupComponent,
        SubLedgerTypeDeleteDialogComponent,
        SubLedgerTypeDeletePopupComponent,
    ],
    providers: [
        SubLedgerTypeService,
        SubLedgerTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerSubLedgerTypeModule {}
