import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherBooking } from './voucher-booking.model';
import { VoucherBookingService } from './voucher-booking.service';

@Component({
    selector: 'jhi-voucher-booking-detail',
    templateUrl: './voucher-booking-detail.component.html'
})
export class VoucherBookingDetailComponent implements OnInit, OnDestroy {

    voucherBooking: VoucherBooking;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voucherBookingService: VoucherBookingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoucherBookings();
    }

    load(id) {
        this.voucherBookingService.find(id)
            .subscribe((voucherBookingResponse: HttpResponse<VoucherBooking>) => {
                this.voucherBooking = voucherBookingResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoucherBookings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voucherBookingListModification',
            (response) => this.load(this.voucherBooking.id)
        );
    }
}
