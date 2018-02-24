import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherValuationType } from './voucher-valuation-type.model';
import { VoucherValuationTypePopupService } from './voucher-valuation-type-popup.service';
import { VoucherValuationTypeService } from './voucher-valuation-type.service';

@Component({
    selector: 'jhi-voucher-valuation-type-dialog',
    templateUrl: './voucher-valuation-type-dialog.component.html'
})
export class VoucherValuationTypeDialogComponent implements OnInit {

    voucherValuationType: VoucherValuationType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private voucherValuationTypeService: VoucherValuationTypeService,
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
        if (this.voucherValuationType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voucherValuationTypeService.update(this.voucherValuationType));
        } else {
            this.subscribeToSaveResponse(
                this.voucherValuationTypeService.create(this.voucherValuationType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VoucherValuationType>>) {
        result.subscribe((res: HttpResponse<VoucherValuationType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VoucherValuationType) {
        this.eventManager.broadcast({ name: 'voucherValuationTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-voucher-valuation-type-popup',
    template: ''
})
export class VoucherValuationTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherValuationTypePopupService: VoucherValuationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voucherValuationTypePopupService
                    .open(VoucherValuationTypeDialogComponent as Component, params['id']);
            } else {
                this.voucherValuationTypePopupService
                    .open(VoucherValuationTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
