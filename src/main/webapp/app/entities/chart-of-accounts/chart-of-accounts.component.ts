import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ChartOfAccounts } from './chart-of-accounts.model';
import { ChartOfAccountsService } from './chart-of-accounts.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-chart-of-accounts',
    templateUrl: './chart-of-accounts.component.html'
})
export class ChartOfAccountsComponent implements OnInit, OnDestroy {
chartOfAccounts: ChartOfAccounts[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chartOfAccountsService: ChartOfAccountsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.chartOfAccountsService.query().subscribe(
            (res: HttpResponse<ChartOfAccounts[]>) => {
                this.chartOfAccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChartOfAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ChartOfAccounts) {
        return item.id;
    }
    registerChangeInChartOfAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('chartOfAccountsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
