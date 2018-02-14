import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LedgerChartOfAccountsModule } from './chart-of-accounts/chart-of-accounts.module';
import { LedgerLedgerAccountModule } from './ledger-account/ledger-account.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LedgerChartOfAccountsModule,
        LedgerLedgerAccountModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerEntityModule {}
