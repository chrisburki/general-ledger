import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    ChartOfAccountsService,
    ChartOfAccountsPopupService,
    ChartOfAccountsComponent,
    ChartOfAccountsDetailComponent,
    ChartOfAccountsDialogComponent,
    ChartOfAccountsPopupComponent,
    ChartOfAccountsDeletePopupComponent,
    ChartOfAccountsDeleteDialogComponent,
    chartOfAccountsRoute,
    chartOfAccountsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chartOfAccountsRoute,
    ...chartOfAccountsPopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ChartOfAccountsComponent,
        ChartOfAccountsDetailComponent,
        ChartOfAccountsDialogComponent,
        ChartOfAccountsDeleteDialogComponent,
        ChartOfAccountsPopupComponent,
        ChartOfAccountsDeletePopupComponent,
    ],
    entryComponents: [
        ChartOfAccountsComponent,
        ChartOfAccountsDialogComponent,
        ChartOfAccountsPopupComponent,
        ChartOfAccountsDeleteDialogComponent,
        ChartOfAccountsDeletePopupComponent,
    ],
    providers: [
        ChartOfAccountsService,
        ChartOfAccountsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerChartOfAccountsModule {}
