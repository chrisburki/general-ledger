import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ChartOfAccounts } from './chart-of-accounts.model';
import { ChartOfAccountsService } from './chart-of-accounts.service';

@Injectable()
export class ChartOfAccountsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private chartOfAccountsService: ChartOfAccountsService

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
                this.chartOfAccountsService.find(id)
                    .subscribe((chartOfAccountsResponse: HttpResponse<ChartOfAccounts>) => {
                        const chartOfAccounts: ChartOfAccounts = chartOfAccountsResponse.body;
                        this.ngbModalRef = this.chartOfAccountsModalRef(component, chartOfAccounts);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.chartOfAccountsModalRef(component, new ChartOfAccounts());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    chartOfAccountsModalRef(component: Component, chartOfAccounts: ChartOfAccounts): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.chartOfAccounts = chartOfAccounts;
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
