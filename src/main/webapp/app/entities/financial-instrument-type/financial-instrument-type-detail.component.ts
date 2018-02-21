import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialInstrumentType } from './financial-instrument-type.model';
import { FinancialInstrumentTypeService } from './financial-instrument-type.service';

@Component({
    selector: 'jhi-financial-instrument-type-detail',
    templateUrl: './financial-instrument-type-detail.component.html'
})
export class FinancialInstrumentTypeDetailComponent implements OnInit, OnDestroy {

    financialInstrumentType: FinancialInstrumentType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private financialInstrumentTypeService: FinancialInstrumentTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinancialInstrumentTypes();
    }

    load(id) {
        this.financialInstrumentTypeService.find(id)
            .subscribe((financialInstrumentTypeResponse: HttpResponse<FinancialInstrumentType>) => {
                this.financialInstrumentType = financialInstrumentTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinancialInstrumentTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'financialInstrumentTypeListModification',
            (response) => this.load(this.financialInstrumentType.id)
        );
    }
}
