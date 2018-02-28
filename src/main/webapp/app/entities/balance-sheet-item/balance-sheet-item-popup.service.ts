import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BalanceSheetItem } from './balance-sheet-item.model';
import { BalanceSheetItemService } from './balance-sheet-item.service';

@Injectable()
export class BalanceSheetItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private balanceSheetItemService: BalanceSheetItemService

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
                this.balanceSheetItemService.find(id)
                    .subscribe((balanceSheetItemResponse: HttpResponse<BalanceSheetItem>) => {
                        const balanceSheetItem: BalanceSheetItem = balanceSheetItemResponse.body;
                        if (balanceSheetItem.balanceDate) {
                            balanceSheetItem.balanceDate = {
                                year: balanceSheetItem.balanceDate.getFullYear(),
                                month: balanceSheetItem.balanceDate.getMonth() + 1,
                                day: balanceSheetItem.balanceDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.balanceSheetItemModalRef(component, balanceSheetItem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.balanceSheetItemModalRef(component, new BalanceSheetItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    balanceSheetItemModalRef(component: Component, balanceSheetItem: BalanceSheetItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.balanceSheetItem = balanceSheetItem;
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
