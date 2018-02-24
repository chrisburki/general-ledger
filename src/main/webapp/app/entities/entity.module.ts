import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LedgerChartOfAccountsModule } from './chart-of-accounts/chart-of-accounts.module';
import { LedgerLedgerAccountModule } from './ledger-account/ledger-account.module';
import { LedgerJournalPostingModule } from './journal-posting/journal-posting.module';
import { LedgerBalanceSheetModule } from './balance-sheet/balance-sheet.module';
import { LedgerFinancialInstrumentTypeModule } from './financial-instrument-type/financial-instrument-type.module';
import { LedgerVoucherAccountTypeModule } from './voucher-account-type/voucher-account-type.module';
import { LedgerVoucherPositionModule } from './voucher-position/voucher-position.module';
import { LedgerVoucherBookingModule } from './voucher-booking/voucher-booking.module';
import { LedgerVoucherValuationModule } from './voucher-valuation/voucher-valuation.module';
import { LedgerSubLedgerTypeModule } from './sub-ledger-type/sub-ledger-type.module';
import { LedgerVoucherValuationTypeModule } from './voucher-valuation-type/voucher-valuation-type.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LedgerChartOfAccountsModule,
        LedgerLedgerAccountModule,
        LedgerJournalPostingModule,
        LedgerBalanceSheetModule,
        LedgerFinancialInstrumentTypeModule,
        LedgerVoucherAccountTypeModule,
        LedgerVoucherPositionModule,
        LedgerVoucherBookingModule,
        LedgerVoucherValuationModule,
        LedgerSubLedgerTypeModule,
        LedgerVoucherValuationTypeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LedgerEntityModule {}
