import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherBooking } from './voucher-booking.model';
import { VoucherBookingPopupService } from './voucher-booking-popup.service';
import { VoucherBookingService } from './voucher-booking.service';

@Component({
    selector: 'jhi-voucher-booking-delete-dialog',
    templateUrl: './voucher-booking-delete-dialog.component.html'
})
export class VoucherBookingDeleteDialogComponent {

    voucherBooking: VoucherBooking;

    constructor(
        private voucherBookingService: VoucherBookingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherBookingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voucherBookingListModification',
                content: 'Deleted an voucherBooking'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-booking-delete-popup',
    template: ''
})
export class VoucherBookingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherBookingPopupService: VoucherBookingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voucherBookingPopupService
                .open(VoucherBookingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
