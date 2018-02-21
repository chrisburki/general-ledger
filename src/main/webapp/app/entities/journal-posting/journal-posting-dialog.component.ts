import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JournalPosting } from './journal-posting.model';
import { JournalPostingPopupService } from './journal-posting-popup.service';
import { JournalPostingService } from './journal-posting.service';
import { LedgerAccount, LedgerAccountService } from '../ledger-account';

@Component({
    selector: 'jhi-journal-posting-dialog',
    templateUrl: './journal-posting-dialog.component.html'
})
export class JournalPostingDialogComponent implements OnInit {

    journalPosting: JournalPosting;
    isSaving: boolean;

    ledgeraccounts: LedgerAccount[];
    bookDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private journalPostingService: JournalPostingService,
        private ledgerAccountService: LedgerAccountService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.ledgerAccountService.query()
            .subscribe((res: HttpResponse<LedgerAccount[]>) => { this.ledgeraccounts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.journalPosting.id !== undefined) {
            this.subscribeToSaveResponse(
                this.journalPostingService.update(this.journalPosting));
        } else {
            this.subscribeToSaveResponse(
                this.journalPostingService.create(this.journalPosting));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<JournalPosting>>) {
        result.subscribe((res: HttpResponse<JournalPosting>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: JournalPosting) {
        this.eventManager.broadcast({ name: 'journalPostingListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLedgerAccountById(index: number, item: LedgerAccount) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-journal-posting-popup',
    template: ''
})
export class JournalPostingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private journalPostingPopupService: JournalPostingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.journalPostingPopupService
                    .open(JournalPostingDialogComponent as Component, params['id']);
            } else {
                this.journalPostingPopupService
                    .open(JournalPostingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
