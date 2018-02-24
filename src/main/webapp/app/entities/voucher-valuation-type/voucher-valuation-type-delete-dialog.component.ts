import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherValuationType } from './voucher-valuation-type.model';
import { VoucherValuationTypePopupService } from './voucher-valuation-type-popup.service';
import { VoucherValuationTypeService } from './voucher-valuation-type.service';

@Component({
    selector: 'jhi-voucher-valuation-type-delete-dialog',
    templateUrl: './voucher-valuation-type-delete-dialog.component.html'
})
export class VoucherValuationTypeDeleteDialogComponent {

    voucherValuationType: VoucherValuationType;

    constructor(
        private voucherValuationTypeService: VoucherValuationTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherValuationTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voucherValuationTypeListModification',
                content: 'Deleted an voucherValuationType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-valuation-type-delete-popup',
    template: ''
})
export class VoucherValuationTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherValuationTypePopupService: VoucherValuationTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voucherValuationTypePopupService
                .open(VoucherValuationTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
