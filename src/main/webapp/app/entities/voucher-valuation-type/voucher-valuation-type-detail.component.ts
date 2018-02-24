import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherValuationType } from './voucher-valuation-type.model';
import { VoucherValuationTypeService } from './voucher-valuation-type.service';

@Component({
    selector: 'jhi-voucher-valuation-type-detail',
    templateUrl: './voucher-valuation-type-detail.component.html'
})
export class VoucherValuationTypeDetailComponent implements OnInit, OnDestroy {

    voucherValuationType: VoucherValuationType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voucherValuationTypeService: VoucherValuationTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoucherValuationTypes();
    }

    load(id) {
        this.voucherValuationTypeService.find(id)
            .subscribe((voucherValuationTypeResponse: HttpResponse<VoucherValuationType>) => {
                this.voucherValuationType = voucherValuationTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoucherValuationTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voucherValuationTypeListModification',
            (response) => this.load(this.voucherValuationType.id)
        );
    }
}
