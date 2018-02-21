import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherAccountType } from './voucher-account-type.model';
import { VoucherAccountTypePopupService } from './voucher-account-type-popup.service';
import { VoucherAccountTypeService } from './voucher-account-type.service';

@Component({
    selector: 'jhi-voucher-account-type-delete-dialog',
    templateUrl: './voucher-account-type-delete-dialog.component.html'
})
export class VoucherAccountTypeDeleteDialogComponent {

    voucherAccountType: VoucherAccountType;

    constructor(
        private voucherAccountTypeService: VoucherAccountTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherAccountTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voucherAccountTypeListModification',
                content: 'Deleted an voucherAccountType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-account-type-delete-popup',
    template: ''
})
export class VoucherAccountTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherAccountTypePopupService: VoucherAccountTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voucherAccountTypePopupService
                .open(VoucherAccountTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
