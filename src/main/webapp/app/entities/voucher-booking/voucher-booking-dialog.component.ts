import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoucherBooking } from './voucher-booking.model';
import { VoucherBookingPopupService } from './voucher-booking-popup.service';
import { VoucherBookingService } from './voucher-booking.service';
import { JournalPosting, JournalPostingService } from '../journal-posting';
import { VoucherPosition, VoucherPositionService } from '../voucher-position';

@Component({
    selector: 'jhi-voucher-booking-dialog',
    templateUrl: './voucher-booking-dialog.component.html'
})
export class VoucherBookingDialogComponent implements OnInit {

    voucherBooking: VoucherBooking;
    isSaving: boolean;

    journalpostings: JournalPosting[];

    voucherpositions: VoucherPosition[];
    doneDateDp: any;
    bookDateDp: any;
    valueDateDp: any;
    transactionDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voucherBookingService: VoucherBookingService,
        private journalPostingService: JournalPostingService,
        private voucherPositionService: VoucherPositionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.journalPostingService.query()
            .subscribe((res: HttpResponse<JournalPosting[]>) => { this.journalpostings = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.voucherPositionService.query()
            .subscribe((res: HttpResponse<VoucherPosition[]>) => { this.voucherpositions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.voucherBooking.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voucherBookingService.update(this.voucherBooking));
        } else {
            this.subscribeToSaveResponse(
                this.voucherBookingService.create(this.voucherBooking));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VoucherBooking>>) {
        result.subscribe((res: HttpResponse<VoucherBooking>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VoucherBooking) {
        this.eventManager.broadcast({ name: 'voucherBookingListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-voucher-booking-popup',
    template: ''
})
export class VoucherBookingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherBookingPopupService: VoucherBookingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voucherBookingPopupService
                    .open(VoucherBookingDialogComponent as Component, params['id']);
            } else {
                this.voucherBookingPopupService
                    .open(VoucherBookingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
