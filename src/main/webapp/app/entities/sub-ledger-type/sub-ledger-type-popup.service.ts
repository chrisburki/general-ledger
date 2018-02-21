import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SubLedgerType } from './sub-ledger-type.model';
import { SubLedgerTypeService } from './sub-ledger-type.service';

@Injectable()
export class SubLedgerTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private subLedgerTypeService: SubLedgerTypeService

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
                this.subLedgerTypeService.find(id)
                    .subscribe((subLedgerTypeResponse: HttpResponse<SubLedgerType>) => {
                        const subLedgerType: SubLedgerType = subLedgerTypeResponse.body;
                        this.ngbModalRef = this.subLedgerTypeModalRef(component, subLedgerType);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.subLedgerTypeModalRef(component, new SubLedgerType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    subLedgerTypeModalRef(component: Component, subLedgerType: SubLedgerType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.subLedgerType = subLedgerType;
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
