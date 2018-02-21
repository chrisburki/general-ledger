import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoucherPosition } from './voucher-position.model';
import { VoucherPositionService } from './voucher-position.service';

@Component({
    selector: 'jhi-voucher-position-detail',
    templateUrl: './voucher-position-detail.component.html'
})
export class VoucherPositionDetailComponent implements OnInit, OnDestroy {

    voucherPosition: VoucherPosition;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voucherPositionService: VoucherPositionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoucherPositions();
    }

    load(id) {
        this.voucherPositionService.find(id)
            .subscribe((voucherPositionResponse: HttpResponse<VoucherPosition>) => {
                this.voucherPosition = voucherPositionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoucherPositions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voucherPositionListModification',
            (response) => this.load(this.voucherPosition.id)
        );
    }
}
