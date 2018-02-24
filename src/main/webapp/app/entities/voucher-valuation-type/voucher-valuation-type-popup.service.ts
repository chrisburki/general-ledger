import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VoucherValuationType } from './voucher-valuation-type.model';
import { VoucherValuationTypeService } from './voucher-valuation-type.service';

@Injectable()
export class VoucherValuationTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private voucherValuationTypeService: VoucherValuationTypeService

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
                this.voucherValuationTypeService.find(id)
                    .subscribe((voucherValuationTypeResponse: HttpResponse<VoucherValuationType>) => {
                        const voucherValuationType: VoucherValuationType = voucherValuationTypeResponse.body;
                        this.ngbModalRef = this.voucherValuationTypeModalRef(component, voucherValuationType);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.voucherValuationTypeModalRef(component, new VoucherValuationType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    voucherValuationTypeModalRef(component: Component, voucherValuationType: VoucherValuationType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.voucherValuationType = voucherValuationType;
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
