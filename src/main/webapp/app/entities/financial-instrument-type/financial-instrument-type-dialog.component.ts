import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialInstrumentType } from './financial-instrument-type.model';
import { FinancialInstrumentTypePopupService } from './financial-instrument-type-popup.service';
import { FinancialInstrumentTypeService } from './financial-instrument-type.service';

@Component({
    selector: 'jhi-financial-instrument-type-dialog',
    templateUrl: './financial-instrument-type-dialog.component.html'
})
export class FinancialInstrumentTypeDialogComponent implements OnInit {

    financialInstrumentType: FinancialInstrumentType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private financialInstrumentTypeService: FinancialInstrumentTypeService,
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
        if (this.financialInstrumentType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.financialInstrumentTypeService.update(this.financialInstrumentType));
        } else {
            this.subscribeToSaveResponse(
                this.financialInstrumentTypeService.create(this.financialInstrumentType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FinancialInstrumentType>>) {
        result.subscribe((res: HttpResponse<FinancialInstrumentType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FinancialInstrumentType) {
        this.eventManager.broadcast({ name: 'financialInstrumentTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-financial-instrument-type-popup',
    template: ''
})
export class FinancialInstrumentTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialInstrumentTypePopupService: FinancialInstrumentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.financialInstrumentTypePopupService
                    .open(FinancialInstrumentTypeDialogComponent as Component, params['id']);
            } else {
                this.financialInstrumentTypePopupService
                    .open(FinancialInstrumentTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
