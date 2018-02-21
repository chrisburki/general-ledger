import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VoucherAccountType } from './voucher-account-type.model';
import { VoucherAccountTypeService } from './voucher-account-type.service';

@Injectable()
export class VoucherAccountTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private voucherAccountTypeService: VoucherAccountTypeService

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
                this.voucherAccountTypeService.find(id)
                    .subscribe((voucherAccountTypeResponse: HttpResponse<VoucherAccountType>) => {
                        const voucherAccountType: VoucherAccountType = voucherAccountTypeResponse.body;
                        this.ngbModalRef = this.voucherAccountTypeModalRef(component, voucherAccountType);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.voucherAccountTypeModalRef(component, new VoucherAccountType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    voucherAccountTypeModalRef(component: Component, voucherAccountType: VoucherAccountType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.voucherAccountType = voucherAccountType;
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
