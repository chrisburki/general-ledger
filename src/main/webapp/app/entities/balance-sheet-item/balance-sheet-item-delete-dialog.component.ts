import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceSheetItem } from './balance-sheet-item.model';
import { BalanceSheetItemPopupService } from './balance-sheet-item-popup.service';
import { BalanceSheetItemService } from './balance-sheet-item.service';

@Component({
    selector: 'jhi-balance-sheet-item-delete-dialog',
    templateUrl: './balance-sheet-item-delete-dialog.component.html'
})
export class BalanceSheetItemDeleteDialogComponent {

    balanceSheetItem: BalanceSheetItem;

    constructor(
        private balanceSheetItemService: BalanceSheetItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.balanceSheetItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'balanceSheetItemListModification',
                content: 'Deleted an balanceSheetItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-balance-sheet-item-delete-popup',
    template: ''
})
export class BalanceSheetItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private balanceSheetItemPopupService: BalanceSheetItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.balanceSheetItemPopupService
                .open(BalanceSheetItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
