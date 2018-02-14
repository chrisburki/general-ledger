import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { LedgerAccount } from './ledger-account.model';
import { LedgerAccountService } from './ledger-account.service';

@Injectable()
export class LedgerAccountPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ledgerAccountService: LedgerAccountService

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
                this.ledgerAccountService.find(id)
                    .subscribe((ledgerAccountResponse: HttpResponse<LedgerAccount>) => {
                        const ledgerAccount: LedgerAccount = ledgerAccountResponse.body;
                        this.ngbModalRef = this.ledgerAccountModalRef(component, ledgerAccount);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.ledgerAccountModalRef(component, new LedgerAccount());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    ledgerAccountModalRef(component: Component, ledgerAccount: LedgerAccount): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ledgerAccount = ledgerAccount;
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
