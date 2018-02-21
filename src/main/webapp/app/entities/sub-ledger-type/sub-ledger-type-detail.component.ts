import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SubLedgerType } from './sub-ledger-type.model';
import { SubLedgerTypeService } from './sub-ledger-type.service';

@Component({
    selector: 'jhi-sub-ledger-type-detail',
    templateUrl: './sub-ledger-type-detail.component.html'
})
export class SubLedgerTypeDetailComponent implements OnInit, OnDestroy {

    subLedgerType: SubLedgerType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private subLedgerTypeService: SubLedgerTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubLedgerTypes();
    }

    load(id) {
        this.subLedgerTypeService.find(id)
            .subscribe((subLedgerTypeResponse: HttpResponse<SubLedgerType>) => {
                this.subLedgerType = subLedgerTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubLedgerTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'subLedgerTypeListModification',
            (response) => this.load(this.subLedgerType.id)
        );
    }
}
