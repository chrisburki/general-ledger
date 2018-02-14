import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LedgerAccount } from './ledger-account.model';
import { LedgerAccountService } from './ledger-account.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-ledger-account',
    templateUrl: './ledger-account.component.html'
})
export class LedgerAccountComponent implements OnInit, OnDestroy {
ledgerAccounts: LedgerAccount[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private ledgerAccountService: LedgerAccountService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.ledgerAccountService.query().subscribe(
            (res: HttpResponse<LedgerAccount[]>) => {
                this.ledgerAccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLedgerAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LedgerAccount) {
        return item.id;
    }
    registerChangeInLedgerAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('ledgerAccountListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
