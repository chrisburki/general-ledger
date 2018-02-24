import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoucherValuationType } from './voucher-valuation-type.model';
import { VoucherValuationTypeService } from './voucher-valuation-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-voucher-valuation-type',
    templateUrl: './voucher-valuation-type.component.html'
})
export class VoucherValuationTypeComponent implements OnInit, OnDestroy {
voucherValuationTypes: VoucherValuationType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private voucherValuationTypeService: VoucherValuationTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.voucherValuationTypeService.query().subscribe(
            (res: HttpResponse<VoucherValuationType[]>) => {
                this.voucherValuationTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVoucherValuationTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VoucherValuationType) {
        return item.id;
    }
    registerChangeInVoucherValuationTypes() {
        this.eventSubscriber = this.eventManager.subscribe('voucherValuationTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
