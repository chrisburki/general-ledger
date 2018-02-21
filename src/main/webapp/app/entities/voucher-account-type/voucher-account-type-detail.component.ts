import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherAccountType } from './voucher-account-type.model';
import { VoucherAccountTypeService } from './voucher-account-type.service';

@Component({
    selector: 'jhi-voucher-account-type-detail',
    templateUrl: './voucher-account-type-detail.component.html'
})
export class VoucherAccountTypeDetailComponent implements OnInit, OnDestroy {

    voucherAccountType: VoucherAccountType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voucherAccountTypeService: VoucherAccountTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoucherAccountTypes();
    }

    load(id) {
        this.voucherAccountTypeService.find(id)
            .subscribe((voucherAccountTypeResponse: HttpResponse<VoucherAccountType>) => {
                this.voucherAccountType = voucherAccountTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoucherAccountTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voucherAccountTypeListModification',
            (response) => this.load(this.voucherAccountType.id)
        );
    }
}
