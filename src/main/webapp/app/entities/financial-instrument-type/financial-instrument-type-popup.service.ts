import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { FinancialInstrumentType } from './financial-instrument-type.model';
import { FinancialInstrumentTypeService } from './financial-instrument-type.service';

@Injectable()
export class FinancialInstrumentTypePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private financialInstrumentTypeService: FinancialInstrumentTypeService

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
                this.financialInstrumentTypeService.find(id)
                    .subscribe((financialInstrumentTypeResponse: HttpResponse<FinancialInstrumentType>) => {
                        const financialInstrumentType: FinancialInstrumentType = financialInstrumentTypeResponse.body;
                        this.ngbModalRef = this.financialInstrumentTypeModalRef(component, financialInstrumentType);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.financialInstrumentTypeModalRef(component, new FinancialInstrumentType());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    financialInstrumentTypeModalRef(component: Component, financialInstrumentType: FinancialInstrumentType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.financialInstrumentType = financialInstrumentType;
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
