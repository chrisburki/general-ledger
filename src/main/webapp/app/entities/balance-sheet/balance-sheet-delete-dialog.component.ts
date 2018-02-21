import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceSheet } from './balance-sheet.model';
import { BalanceSheetPopupService } from './balance-sheet-popup.service';
import { BalanceSheetService } from './balance-sheet.service';

@Component({
    selector: 'jhi-balance-sheet-delete-dialog',
    templateUrl: './balance-sheet-delete-dialog.component.html'
})
export class BalanceSheetDeleteDialogComponent {

    balanceSheet: BalanceSheet;

    constructor(
        private balanceSheetService: BalanceSheetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.balanceSheetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'balanceSheetListModification',
                content: 'Deleted an balanceSheet'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-balance-sheet-delete-popup',
    template: ''
})
export class BalanceSheetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceSheetPopupService: BalanceSheetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.balanceSheetPopupService
                .open(BalanceSheetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
