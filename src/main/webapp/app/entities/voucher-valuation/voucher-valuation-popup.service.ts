import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VoucherValuation } from './voucher-valuation.model';
import { VoucherValuationService } from './voucher-valuation.service';

@Injectable()
export class VoucherValuationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private voucherValuationService: VoucherValuationService

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
                this.voucherValuationService.find(id)
                    .subscribe((voucherValuationResponse: HttpResponse<VoucherValuation>) => {
                        const voucherValuation: VoucherValuation = voucherValuationResponse.body;
                        this.ngbModalRef = this.voucherValuationModalRef(component, voucherValuation);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.voucherValuationModalRef(component, new VoucherValuation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    voucherValuationModalRef(component: Component, voucherValuation: VoucherValuation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.voucherValuation = voucherValuation;
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
