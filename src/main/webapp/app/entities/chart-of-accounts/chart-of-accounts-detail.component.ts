import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ChartOfAccounts } from './chart-of-accounts.model';
import { ChartOfAccountsService } from './chart-of-accounts.service';

@Component({
    selector: 'jhi-chart-of-accounts-detail',
    templateUrl: './chart-of-accounts-detail.component.html'
})
export class ChartOfAccountsDetailComponent implements OnInit, OnDestroy {

    chartOfAccounts: ChartOfAccounts;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chartOfAccountsService: ChartOfAccountsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChartOfAccounts();
    }

    load(id) {
        this.chartOfAccountsService.find(id)
            .subscribe((chartOfAccountsResponse: HttpResponse<ChartOfAccounts>) => {
                this.chartOfAccounts = chartOfAccountsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChartOfAccounts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chartOfAccountsListModification',
            (response) => this.load(this.chartOfAccounts.id)
        );
    }
}
