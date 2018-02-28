import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VoucherBooking } from './voucher-booking.model';
import { VoucherBookingService } from './voucher-booking.service';

@Injectable()
export class VoucherBookingPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private voucherBookingService: VoucherBookingService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.voucherBookingService.find(id)
                    .subscribe((voucherBookingResponse: HttpResponse<VoucherBooking>) => {
                        const voucherBooking: VoucherBooking = voucherBookingResponse.body;
                        if (voucherBooking.doneDate) {
                            voucherBooking.doneDate = {
                                year: voucherBooking.doneDate.getFullYear(),
                                month: voucherBooking.doneDate.getMonth() + 1,
                                day: voucherBooking.doneDate.getDate()
                            };
                        }
                        if (voucherBooking.bookDate) {
                            voucherBooking.bookDate = {
                                year: voucherBooking.bookDate.getFullYear(),
                                month: voucherBooking.bookDate.getMonth() + 1,
                                day: voucherBooking.bookDate.getDate()
                            };
                        }
                        if (voucherBooking.valueDate) {
                            voucherBooking.valueDate = {
                                year: voucherBooking.valueDate.getFullYear(),
                                month: voucherBooking.valueDate.getMonth() + 1,
                                day: voucherBooking.valueDate.getDate()
                            };
                        }
                        if (voucherBooking.transactionDate) {
                            voucherBooking.transactionDate = {
                                year: voucherBooking.transactionDate.getFullYear(),
                                month: voucherBooking.transactionDate.getMonth() + 1,
                                day: voucherBooking.transactionDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.voucherBookingModalRef(component, voucherBooking);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.voucherBookingModalRef(component, new VoucherBooking());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    voucherBookingModalRef(component: Component, voucherBooking: VoucherBooking): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.voucherBooking = voucherBooking;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
