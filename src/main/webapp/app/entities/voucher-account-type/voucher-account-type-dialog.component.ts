import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherAccountType } from './voucher-account-type.model';
import { VoucherAccountTypePopupService } from './voucher-account-type-popup.service';
import { VoucherAccountTypeService } from './voucher-account-type.service';

@Component({
    selector: 'jhi-voucher-account-type-dialog',
    templateUrl: './voucher-account-type-dialog.component.html'
})
export class VoucherAccountTypeDialogComponent implements OnInit {

    voucherAccountType: VoucherAccountType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private voucherAccountTypeService: VoucherAccountTypeService,
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
        if (this.voucherAccountType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voucherAccountTypeService.update(this.voucherAccountType));
        } else {
            this.subscribeToSaveResponse(
                this.voucherAccountTypeService.create(this.voucherAccountType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VoucherAccountType>>) {
        result.subscribe((res: HttpResponse<VoucherAccountType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VoucherAccountType) {
        this.eventManager.broadcast({ name: 'voucherAccountTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-voucher-account-type-popup',
    template: ''
})
export class VoucherAccountTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherAccountTypePopupService: VoucherAccountTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voucherAccountTypePopupService
                    .open(VoucherAccountTypeDialogComponent as Component, params['id']);
            } else {
                this.voucherAccountTypePopupService
                    .open(VoucherAccountTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
