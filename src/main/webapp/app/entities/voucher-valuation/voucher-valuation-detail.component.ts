import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherValuation } from './voucher-valuation.model';
import { VoucherValuationService } from './voucher-valuation.service';

@Component({
    selector: 'jhi-voucher-valuation-detail',
    templateUrl: './voucher-valuation-detail.component.html'
})
export class VoucherValuationDetailComponent implements OnInit, OnDestroy {

    voucherValuation: VoucherValuation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voucherValuationService: VoucherValuationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoucherValuations();
    }

    load(id) {
        this.voucherValuationService.find(id)
            .subscribe((voucherValuationResponse: HttpResponse<VoucherValuation>) => {
                this.voucherValuation = voucherValuationResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoucherValuations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voucherValuationListModification',
            (response) => this.load(this.voucherValuation.id)
        );
    }
}
