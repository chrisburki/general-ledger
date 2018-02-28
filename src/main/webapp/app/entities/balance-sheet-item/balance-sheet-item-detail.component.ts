import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BalanceSheetItem } from './balance-sheet-item.model';
import { BalanceSheetItemService } from './balance-sheet-item.service';

@Component({
    selector: 'jhi-balance-sheet-item-detail',
    templateUrl: './balance-sheet-item-detail.component.html'
})
export class BalanceSheetItemDetailComponent implements OnInit, OnDestroy {

    balanceSheetItem: BalanceSheetItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private balanceSheetItemService: BalanceSheetItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBalanceSheetItems();
    }

    load(id) {
        this.balanceSheetItemService.find(id)
            .subscribe((balanceSheetItemResponse: HttpResponse<BalanceSheetItem>) => {
                this.balanceSheetItem = balanceSheetItemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBalanceSheetItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'balanceSheetItemListModification',
            (response) => this.load(this.balanceSheetItem.id)
        );
    }
}
