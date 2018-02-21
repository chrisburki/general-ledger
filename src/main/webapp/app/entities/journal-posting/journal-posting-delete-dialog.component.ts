import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JournalPosting } from './journal-posting.model';
import { JournalPostingPopupService } from './journal-posting-popup.service';
import { JournalPostingService } from './journal-posting.service';

@Component({
    selector: 'jhi-journal-posting-delete-dialog',
    templateUrl: './journal-posting-delete-dialog.component.html'
})
export class JournalPostingDeleteDialogComponent {

    journalPosting: JournalPosting;

    constructor(
        private journalPostingService: JournalPostingService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.journalPostingService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'journalPostingListModification',
                content: 'Deleted an journalPosting'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-journal-posting-delete-popup',
    template: ''
})
export class JournalPostingDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private journalPostingPopupService: JournalPostingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.journalPostingPopupService
                .open(JournalPostingDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
