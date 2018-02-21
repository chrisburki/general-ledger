import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherPosition } from './voucher-position.model';
import { VoucherPositionPopupService } from './voucher-position-popup.service';
import { VoucherPositionService } from './voucher-position.service';

@Component({
    selector: 'jhi-voucher-position-delete-dialog',
    templateUrl: './voucher-position-delete-dialog.component.html'
})
export class VoucherPositionDeleteDialogComponent {

    voucherPosition: VoucherPosition;

    constructor(
        private voucherPositionService: VoucherPositionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voucherPositionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voucherPositionListModification',
                content: 'Deleted an voucherPosition'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-voucher-position-delete-popup',
    template: ''
})
export class VoucherPositionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voucherPositionPopupService: VoucherPositionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voucherPositionPopupService
                .open(VoucherPositionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
