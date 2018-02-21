import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceSheet } from './balance-sheet.model';
import { BalanceSheetService } from './balance-sheet.service';

@Component({
    selector: 'jhi-balance-sheet-detail',
    templateUrl: './balance-sheet-detail.component.html'
})
export class BalanceSheetDetailComponent implements OnInit, OnDestroy {

    balanceSheet: BalanceSheet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private balanceSheetService: BalanceSheetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBalanceSheets();
    }

    load(id) {
        this.balanceSheetService.find(id)
            .subscribe((balanceSheetResponse: HttpResponse<BalanceSheet>) => {
                this.balanceSheet = balanceSheetResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBalanceSheets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'balanceSheetListModification',
            (response) => this.load(this.balanceSheet.id)
        );
    }
}
