import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BalanceSheet } from './balance-sheet.model';
import { BalanceSheetPopupService } from './balance-sheet-popup.service';
import { BalanceSheetService } from './balance-sheet.service';
import { ChartOfAccounts, ChartOfAccountsService } from '../chart-of-accounts';
import { LedgerAccount, LedgerAccountService } from '../ledger-account';

@Component({
    selector: 'jhi-balance-sheet-dialog',
    templateUrl: './balance-sheet-dialog.component.html'
})
export class BalanceSheetDialogComponent implements OnInit {

    balanceSheet: BalanceSheet;
    isSaving: boolean;

    chartofaccounts: ChartOfAccounts[];

    ledgeraccounts: LedgerAccount[];
    balanceDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private balanceSheetService: BalanceSheetService,
        private chartOfAccountsService: ChartOfAccountsService,
        private ledgerAccountService: LedgerAccountService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.chartOfAccountsService.query()
            .subscribe((res: HttpResponse<ChartOfAccounts[]>) => { this.chartofaccounts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ledgerAccountService.query()
            .subscribe((res: HttpResponse<LedgerAccount[]>) => { this.ledgeraccounts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.balanceSheet.id !== undefined) {
            this.subscribeToSaveResponse(
                this.balanceSheetService.update(this.balanceSheet));
        } else {
            this.subscribeToSaveResponse(
                this.balanceSheetService.create(this.balanceSheet));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BalanceSheet>>) {
        result.subscribe((res: HttpResponse<BalanceSheet>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BalanceSheet) {
        this.eventManager.broadcast({ name: 'balanceSheetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackChartOfAccountsById(index: number, item: ChartOfAccounts) {
        return item.id;
    }

    trackLedgerAccountById(index: number, item: LedgerAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-balance-sheet-popup',
    template: ''
})
export class BalanceSheetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceSheetPopupService: BalanceSheetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.balanceSheetPopupService
                    .open(BalanceSheetDialogComponent as Component, params['id']);
            } else {
                this.balanceSheetPopupService
                    .open(BalanceSheetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
