import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    JournalPostingService,
    JournalPostingPopupService,
    JournalPostingComponent,
    JournalPostingDetailComponent,
    JournalPostingDialogComponent,
    JournalPostingPopupComponent,
    JournalPostingDeletePopupComponent,
    JournalPostingDeleteDialogComponent,
    journalPostingRoute,
    journalPostingPopupRoute,
    JournalPostingResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...journalPostingRoute,
    ...journalPostingPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JournalPostingComponent,
        JournalPostingDetailComponent,
        JournalPostingDialogComponent,
        JournalPostingDeleteDialogComponent,
        JournalPostingPopupComponent,
        JournalPostingDeletePopupComponent,
    ],
    entryComponents: [
        JournalPostingComponent,
        JournalPostingDialogComponent,
        JournalPostingPopupComponent,
        JournalPostingDeleteDialogComponent,
        JournalPostingDeletePopupComponent,
    ],
    providers: [
        JournalPostingService,
        JournalPostingPopupService,
        JournalPostingResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerJournalPostingModule {}
