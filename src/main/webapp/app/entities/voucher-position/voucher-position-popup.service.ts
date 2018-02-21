import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VoucherPosition } from './voucher-position.model';
import { VoucherPositionService } from './voucher-position.service';

@Injectable()
export class VoucherPositionPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private voucherPositionService: VoucherPositionService

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
                this.voucherPositionService.find(id)
                    .subscribe((voucherPositionResponse: HttpResponse<VoucherPosition>) => {
                        const voucherPosition: VoucherPosition = voucherPositionResponse.body;
                        this.ngbModalRef = this.voucherPositionModalRef(component, voucherPosition);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.voucherPositionModalRef(component, new VoucherPosition());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    voucherPositionModalRef(component: Component, voucherPosition: VoucherPosition): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.voucherPosition = voucherPosition;
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
