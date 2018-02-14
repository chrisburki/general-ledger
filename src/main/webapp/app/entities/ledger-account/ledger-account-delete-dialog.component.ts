import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerAccount } from './ledger-account.model';
import { LedgerAccountPopupService } from './ledger-account-popup.service';
import { LedgerAccountService } from './ledger-account.service';

@Component({
    selector: 'jhi-ledger-account-delete-dialog',
    templateUrl: './ledger-account-delete-dialog.component.html'
})
export class LedgerAccountDeleteDialogComponent {

    ledgerAccount: LedgerAccount;

    constructor(
        private ledgerAccountService: LedgerAccountService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ledgerAccountService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ledgerAccountListModification',
                content: 'Deleted an ledgerAccount'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ledger-account-delete-popup',
    template: ''
})
export class LedgerAccountDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ledgerAccountPopupService: LedgerAccountPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ledgerAccountPopupService
                .open(LedgerAccountDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
