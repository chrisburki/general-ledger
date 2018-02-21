import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinancialInstrumentType } from './financial-instrument-type.model';
import { FinancialInstrumentTypePopupService } from './financial-instrument-type-popup.service';
import { FinancialInstrumentTypeService } from './financial-instrument-type.service';

@Component({
    selector: 'jhi-financial-instrument-type-delete-dialog',
    templateUrl: './financial-instrument-type-delete-dialog.component.html'
})
export class FinancialInstrumentTypeDeleteDialogComponent {

    financialInstrumentType: FinancialInstrumentType;

    constructor(
        private financialInstrumentTypeService: FinancialInstrumentTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.financialInstrumentTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'financialInstrumentTypeListModification',
                content: 'Deleted an financialInstrumentType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-financial-instrument-type-delete-popup',
    template: ''
})
export class FinancialInstrumentTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private financialInstrumentTypePopupService: FinancialInstrumentTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.financialInstrumentTypePopupService
                .open(FinancialInstrumentTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
