import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BalanceSheetItem } from './balance-sheet-item.model';
import { BalanceSheetItemPopupService } from './balance-sheet-item-popup.service';
import { BalanceSheetItemService } from './balance-sheet-item.service';
import { BalanceSheet, BalanceSheetService } from '../balance-sheet';
import { LedgerAccount, LedgerAccountService } from '../ledger-account';

@Component({
    selector: 'jhi-balance-sheet-item-dialog',
    templateUrl: './balance-sheet-item-dialog.component.html'
})
export class BalanceSheetItemDialogComponent implements OnInit {

    balanceSheetItem: BalanceSheetItem;
    isSaving: boolean;

    balancesheets: BalanceSheet[];

    ledgeraccounts: LedgerAccount[];
    balanceDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private balanceSheetItemService: BalanceSheetItemService,
        private balanceSheetService: BalanceSheetService,
        private ledgerAccountService: LedgerAccountService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.balanceSheetService.query()
            .subscribe((res: HttpResponse<BalanceSheet[]>) => { this.balancesheets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.ledgerAccountService.query()
            .subscribe((res: HttpResponse<LedgerAccount[]>) => { this.ledgeraccounts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.balanceSheetItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.balanceSheetItemService.update(this.balanceSheetItem));
        } else {
            this.subscribeToSaveResponse(
                this.balanceSheetItemService.create(this.balanceSheetItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BalanceSheetItem>>) {
        result.subscribe((res: HttpResponse<BalanceSheetItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BalanceSheetItem) {
        this.eventManager.broadcast({ name: 'balanceSheetItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBalanceSheetById(index: number, item: BalanceSheet) {
        return item.id;
    }

    trackLedgerAccountById(index: number, item: LedgerAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-balance-sheet-item-popup',
    template: ''
})
export class BalanceSheetItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceSheetItemPopupService: BalanceSheetItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.balanceSheetItemPopupService
                    .open(BalanceSheetItemDialogComponent as Component, params['id']);
            } else {
                this.balanceSheetItemPopupService
                    .open(BalanceSheetItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
