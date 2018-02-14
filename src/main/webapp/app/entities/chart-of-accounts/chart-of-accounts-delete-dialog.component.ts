import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChartOfAccounts } from './chart-of-accounts.model';
import { ChartOfAccountsPopupService } from './chart-of-accounts-popup.service';
import { ChartOfAccountsService } from './chart-of-accounts.service';

@Component({
    selector: 'jhi-chart-of-accounts-delete-dialog',
    templateUrl: './chart-of-accounts-delete-dialog.component.html'
})
export class ChartOfAccountsDeleteDialogComponent {

    chartOfAccounts: ChartOfAccounts;

    constructor(
        private chartOfAccountsService: ChartOfAccountsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chartOfAccountsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chartOfAccountsListModification',
                content: 'Deleted an chartOfAccounts'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chart-of-accounts-delete-popup',
    template: ''
})
export class ChartOfAccountsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chartOfAccountsPopupService: ChartOfAccountsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chartOfAccountsPopupService
                .open(ChartOfAccountsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
