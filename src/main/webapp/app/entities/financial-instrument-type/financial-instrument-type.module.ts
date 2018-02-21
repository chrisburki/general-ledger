import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LedgerSharedModule } from '../../shared';
import {
    FinancialInstrumentTypeService,
    FinancialInstrumentTypePopupService,
    FinancialInstrumentTypeComponent,
    FinancialInstrumentTypeDetailComponent,
    FinancialInstrumentTypeDialogComponent,
    FinancialInstrumentTypePopupComponent,
    FinancialInstrumentTypeDeletePopupComponent,
    FinancialInstrumentTypeDeleteDialogComponent,
    financialInstrumentTypeRoute,
    financialInstrumentTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...financialInstrumentTypeRoute,
    ...financialInstrumentTypePopupRoute,
];

@NgModule({
    imports: [
        LedgerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FinancialInstrumentTypeComponent,
        FinancialInstrumentTypeDetailComponent,
        FinancialInstrumentTypeDialogComponent,
        FinancialInstrumentTypeDeleteDialogComponent,
        FinancialInstrumentTypePopupComponent,
        FinancialInstrumentTypeDeletePopupComponent,
    ],
    entryComponents: [
        FinancialInstrumentTypeComponent,
        FinancialInstrumentTypeDialogComponent,
        FinancialInstrumentTypePopupComponent,
        FinancialInstrumentTypeDeleteDialogComponent,
        FinancialInstrumentTypeDeletePopupComponent,
    ],
    providers: [
        FinancialInstrumentTypeService,
        FinancialInstrumentTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerFinancialInstrumentTypeModule {}
