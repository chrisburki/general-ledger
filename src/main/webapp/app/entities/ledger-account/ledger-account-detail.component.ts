import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerAccount } from './ledger-account.model';
import { LedgerAccountService } from './ledger-account.service';

@Component({
    selector: 'jhi-ledger-account-detail',
    templateUrl: './ledger-account-detail.component.html'
})
export class LedgerAccountDetailComponent implements OnInit, OnDestroy {

    ledgerAccount: LedgerAccount;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private ledgerAccountService: LedgerAccountService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLedgerAccounts();
    }

    load(id) {
        this.ledgerAccountService.find(id)
            .subscribe((ledgerAccountResponse: HttpResponse<LedgerAccount>) => {
                this.ledgerAccount = ledgerAccountResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLedgerAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'ledgerAccountListModification',
            (response) => this.load(this.ledgerAccount.id)
        );
    }
}
