import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChartOfAccounts } from './chart-of-accounts.model';
import { ChartOfAccountsPopupService } from './chart-of-accounts-popup.service';
import { ChartOfAccountsService } from './chart-of-accounts.service';

@Component({
    selector: 'jhi-chart-of-accounts-dialog',
    templateUrl: './chart-of-accounts-dialog.component.html'
})
export class ChartOfAccountsDialogComponent implements OnInit {

    chartOfAccounts: ChartOfAccounts;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private chartOfAccountsService: ChartOfAccountsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chartOfAccounts.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chartOfAccountsService.update(this.chartOfAccounts));
        } else {
            this.subscribeToSaveResponse(
                this.chartOfAccountsService.create(this.chartOfAccounts));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ChartOfAccounts>>) {
        result.subscribe((res: HttpResponse<ChartOfAccounts>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ChartOfAccounts) {
        this.eventManager.broadcast({ name: 'chartOfAccountsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-chart-of-accounts-popup',
    template: ''
})
export class ChartOfAccountsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chartOfAccountsPopupService: ChartOfAccountsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chartOfAccountsPopupService
                    .open(ChartOfAccountsDialogComponent as Component, params['id']);
            } else {
                this.chartOfAccountsPopupService
                    .open(ChartOfAccountsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
