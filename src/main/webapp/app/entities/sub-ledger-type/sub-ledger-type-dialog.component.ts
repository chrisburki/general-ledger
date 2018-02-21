import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubLedgerType } from './sub-ledger-type.model';
import { SubLedgerTypePopupService } from './sub-ledger-type-popup.service';
import { SubLedgerTypeService } from './sub-ledger-type.service';

@Component({
    selector: 'jhi-sub-ledger-type-dialog',
    templateUrl: './sub-ledger-type-dialog.component.html'
})
export class SubLedgerTypeDialogComponent implements OnInit {

    subLedgerType: SubLedgerType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private subLedgerTypeService: SubLedgerTypeService,
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
        if (this.subLedgerType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subLedgerTypeService.update(this.subLedgerType));
        } else {
            this.subscribeToSaveResponse(
                this.subLedgerTypeService.create(this.subLedgerType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SubLedgerType>>) {
        result.subscribe((res: HttpResponse<SubLedgerType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SubLedgerType) {
        this.eventManager.broadcast({ name: 'subLedgerTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sub-ledger-type-popup',
    template: ''
})
export class SubLedgerTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subLedgerTypePopupService: SubLedgerTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subLedgerTypePopupService
                    .open(SubLedgerTypeDialogComponent as Component, params['id']);
            } else {
                this.subLedgerTypePopupService
                    .open(SubLedgerTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
