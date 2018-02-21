import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubLedgerType } from './sub-ledger-type.model';
import { SubLedgerTypePopupService } from './sub-ledger-type-popup.service';
import { SubLedgerTypeService } from './sub-ledger-type.service';

@Component({
    selector: 'jhi-sub-ledger-type-delete-dialog',
    templateUrl: './sub-ledger-type-delete-dialog.component.html'
})
export class SubLedgerTypeDeleteDialogComponent {

    subLedgerType: SubLedgerType;

    constructor(
        private subLedgerTypeService: SubLedgerTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subLedgerTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'subLedgerTypeListModification',
                content: 'Deleted an subLedgerType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sub-ledger-type-delete-popup',
    template: ''
})
export class SubLedgerTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subLedgerTypePopupService: SubLedgerTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.subLedgerTypePopupService
                .open(SubLedgerTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
