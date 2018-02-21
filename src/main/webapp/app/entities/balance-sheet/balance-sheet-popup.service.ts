import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BalanceSheet } from './balance-sheet.model';
import { BalanceSheetService } from './balance-sheet.service';

@Injectable()
export class BalanceSheetPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private balanceSheetService: BalanceSheetService

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
                this.balanceSheetService.find(id)
                    .subscribe((balanceSheetResponse: HttpResponse<BalanceSheet>) => {
                        const balanceSheet: BalanceSheet = balanceSheetResponse.body;
                        if (balanceSheet.balanceDate) {
                            balanceSheet.balanceDate = {
                                year: balanceSheet.balanceDate.getFullYear(),
                                month: balanceSheet.balanceDate.getMonth() + 1,
                                day: balanceSheet.balanceDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.balanceSheetModalRef(component, balanceSheet);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.balanceSheetModalRef(component, new BalanceSheet());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    balanceSheetModalRef(component: Component, balanceSheet: BalanceSheet): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.balanceSheet = balanceSheet;
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
