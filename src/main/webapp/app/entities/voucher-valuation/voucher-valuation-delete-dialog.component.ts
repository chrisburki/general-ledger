import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherValuation } from './voucher-valuation.model';
import { VoucherValuationPopupService } from './voucher-valuation-popup.service';
import { VoucherValuationService } from './voucher-valuation.service';

@Component({
    selector: 'jhi-voucher-valuation-delete-dialog',
    templateUrl: './voucher-valuation-delete-dialog.component.html'
})
export class VoucherValuationDeleteDialogComponent {

    voucherValuation: VoucherValuation;

    constructor(
        private voucherValuationService: VoucherValuationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherValuationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voucherValuationListModification',
                content: 'Deleted an voucherValuation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-valuation-delete-popup',
    template: ''
})
export class VoucherValuationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherValuationPopupService: VoucherValuationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voucherValuationPopupService
                .open(VoucherValuationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
