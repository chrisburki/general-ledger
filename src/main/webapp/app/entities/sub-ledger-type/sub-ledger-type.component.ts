import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SubLedgerType } from './sub-ledger-type.model';
import { SubLedgerTypeService } from './sub-ledger-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sub-ledger-type',
    templateUrl: './sub-ledger-type.component.html'
})
export class SubLedgerTypeComponent implements OnInit, OnDestroy {
subLedgerTypes: SubLedgerType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private subLedgerTypeService: SubLedgerTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.subLedgerTypeService.query().subscribe(
            (res: HttpResponse<SubLedgerType[]>) => {
                this.subLedgerTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSubLedgerTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SubLedgerType) {
        return item.id;
    }
    registerChangeInSubLedgerTypes() {
        this.eventSubscriber = this.eventManager.subscribe('subLedgerTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
