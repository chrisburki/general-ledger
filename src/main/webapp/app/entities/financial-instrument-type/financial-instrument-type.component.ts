import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FinancialInstrumentType } from './financial-instrument-type.model';
import { FinancialInstrumentTypeService } from './financial-instrument-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-financial-instrument-type',
    templateUrl: './financial-instrument-type.component.html'
})
export class FinancialInstrumentTypeComponent implements OnInit, OnDestroy {
financialInstrumentTypes: FinancialInstrumentType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private financialInstrumentTypeService: FinancialInstrumentTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.financialInstrumentTypeService.query().subscribe(
            (res: HttpResponse<FinancialInstrumentType[]>) => {
                this.financialInstrumentTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFinancialInstrumentTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FinancialInstrumentType) {
        return item.id;
    }
    registerChangeInFinancialInstrumentTypes() {
        this.eventSubscriber = this.eventManager.subscribe('financialInstrumentTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
