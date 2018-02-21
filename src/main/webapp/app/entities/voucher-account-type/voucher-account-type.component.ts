import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoucherAccountType } from './voucher-account-type.model';
import { VoucherAccountTypeService } from './voucher-account-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-voucher-account-type',
    templateUrl: './voucher-account-type.component.html'
})
export class VoucherAccountTypeComponent implements OnInit, OnDestroy {
voucherAccountTypes: VoucherAccountType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private voucherAccountTypeService: VoucherAccountTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.voucherAccountTypeService.query().subscribe(
            (res: HttpResponse<VoucherAccountType[]>) => {
                this.voucherAccountTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVoucherAccountTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VoucherAccountType) {
        return item.id;
    }
    registerChangeInVoucherAccountTypes() {
        this.eventSubscriber = this.eventManager.subscribe('voucherAccountTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
