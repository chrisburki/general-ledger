import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LedgerAccount } from './ledger-account.model';
import { LedgerAccountPopupService } from './ledger-account-popup.service';
import { LedgerAccountService } from './ledger-account.service';
import { ChartOfAccounts, ChartOfAccountsService } from '../chart-of-accounts';

@Component({
    selector: 'jhi-ledger-account-dialog',
    templateUrl: './ledger-account-dialog.component.html'
})
export class LedgerAccountDialogComponent implements OnInit {

    ledgerAccount: LedgerAccount;
    isSaving: boolean;

    chartofaccounts: ChartOfAccounts[];

    ledgeraccounts: LedgerAccount[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private ledgerAccountService: LedgerAccountService,
        private chartOfAccountsService: ChartOfAccountsService,
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
        if (this.ledgerAccount.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ledgerAccountService.update(this.ledgerAccount));
        } else {
            this.subscribeToSaveResponse(
                this.ledgerAccountService.create(this.ledgerAccount));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<LedgerAccount>>) {
        result.subscribe((res: HttpResponse<LedgerAccount>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: LedgerAccount) {
        this.eventManager.broadcast({ name: 'ledgerAccountListModification', content: 'OK'});
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
    selector: 'jhi-ledger-account-popup',
    template: ''
})
export class LedgerAccountPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ledgerAccountPopupService: LedgerAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ledgerAccountPopupService
                    .open(LedgerAccountDialogComponent as Component, params['id']);
            } else {
                this.ledgerAccountPopupService
                    .open(LedgerAccountDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
