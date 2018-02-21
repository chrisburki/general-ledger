import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoucherPosition } from './voucher-position.model';
import { VoucherPositionPopupService } from './voucher-position-popup.service';
import { VoucherPositionService } from './voucher-position.service';
import { FinancialInstrumentType, FinancialInstrumentTypeService } from '../financial-instrument-type';
import { VoucherAccountType, VoucherAccountTypeService } from '../voucher-account-type';
import { SubLedgerType, SubLedgerTypeService } from '../sub-ledger-type';

@Component({
    selector: 'jhi-voucher-position-dialog',
    templateUrl: './voucher-position-dialog.component.html'
})
export class VoucherPositionDialogComponent implements OnInit {

    voucherPosition: VoucherPosition;
    isSaving: boolean;

    financialinstrumenttypes: FinancialInstrumentType[];

    voucheraccounttypes: VoucherAccountType[];

    subledgertypes: SubLedgerType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voucherPositionService: VoucherPositionService,
        private financialInstrumentTypeService: FinancialInstrumentTypeService,
        private voucherAccountTypeService: VoucherAccountTypeService,
        private subLedgerTypeService: SubLedgerTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.financialInstrumentTypeService.query()
            .subscribe((res: HttpResponse<FinancialInstrumentType[]>) => { this.financialinstrumenttypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherAccountTypeService.query()
            .subscribe((res: HttpResponse<VoucherAccountType[]>) => { this.voucheraccounttypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.subLedgerTypeService.query()
            .subscribe((res: HttpResponse<SubLedgerType[]>) => { this.subledgertypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.voucherPosition.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voucherPositionService.update(this.voucherPosition));
        } else {
            this.subscribeToSaveResponse(
                this.voucherPositionService.create(this.voucherPosition));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VoucherPosition>>) {
        result.subscribe((res: HttpResponse<VoucherPosition>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VoucherPosition) {
        this.eventManager.broadcast({ name: 'voucherPositionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFinancialInstrumentTypeById(index: number, item: FinancialInstrumentType) {
        return item.id;
    }

    trackVoucherAccountTypeById(index: number, item: VoucherAccountType) {
        return item.id;
    }

    trackSubLedgerTypeById(index: number, item: SubLedgerType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-voucher-position-popup',
    template: ''
})
export class VoucherPositionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherPositionPopupService: VoucherPositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voucherPositionPopupService
                    .open(VoucherPositionDialogComponent as Component, params['id']);
            } else {
                this.voucherPositionPopupService
                    .open(VoucherPositionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
