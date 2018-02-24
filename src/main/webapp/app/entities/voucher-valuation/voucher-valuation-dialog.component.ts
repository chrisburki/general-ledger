import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoucherValuation } from './voucher-valuation.model';
import { VoucherValuationPopupService } from './voucher-valuation-popup.service';
import { VoucherValuationService } from './voucher-valuation.service';
import { JournalPosting, JournalPostingService } from '../journal-posting';
import { VoucherPosition, VoucherPositionService } from '../voucher-position';
import { VoucherValuationType, VoucherValuationTypeService } from '../voucher-valuation-type';

@Component({
    selector: 'jhi-voucher-valuation-dialog',
    templateUrl: './voucher-valuation-dialog.component.html'
})
export class VoucherValuationDialogComponent implements OnInit {

    voucherValuation: VoucherValuation;
    isSaving: boolean;

    journalpostings: JournalPosting[];

    voucherpositions: VoucherPosition[];

    vouchervaluationtypes: VoucherValuationType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voucherValuationService: VoucherValuationService,
        private journalPostingService: JournalPostingService,
        private voucherPositionService: VoucherPositionService,
        private voucherValuationTypeService: VoucherValuationTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.journalPostingService.query()
            .subscribe((res: HttpResponse<JournalPosting[]>) => { this.journalpostings = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherPositionService.query()
            .subscribe((res: HttpResponse<VoucherPosition[]>) => { this.voucherpositions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherValuationTypeService.query()
            .subscribe((res: HttpResponse<VoucherValuationType[]>) => { this.vouchervaluationtypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.voucherValuation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voucherValuationService.update(this.voucherValuation));
        } else {
            this.subscribeToSaveResponse(
                this.voucherValuationService.create(this.voucherValuation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VoucherValuation>>) {
        result.subscribe((res: HttpResponse<VoucherValuation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VoucherValuation) {
        this.eventManager.broadcast({ name: 'voucherValuationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackJournalPostingById(index: number, item: JournalPosting) {
        return item.id;
    }

    trackVoucherPositionById(index: number, item: VoucherPosition) {
        return item.id;
    }

    trackVoucherValuationTypeById(index: number, item: VoucherValuationType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-voucher-valuation-popup',
    template: ''
})
export class VoucherValuationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherValuationPopupService: VoucherValuationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voucherValuationPopupService
                    .open(VoucherValuationDialogComponent as Component, params['id']);
            } else {
                this.voucherValuationPopupService
                    .open(VoucherValuationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
