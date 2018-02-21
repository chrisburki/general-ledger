import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { JournalPosting } from './journal-posting.model';
import { JournalPostingService } from './journal-posting.service';

@Component({
    selector: 'jhi-journal-posting-detail',
    templateUrl: './journal-posting-detail.component.html'
})
export class JournalPostingDetailComponent implements OnInit, OnDestroy {

    journalPosting: JournalPosting;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private journalPostingService: JournalPostingService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJournalPostings();
    }

    load(id) {
        this.journalPostingService.find(id)
            .subscribe((journalPostingResponse: HttpResponse<JournalPosting>) => {
                this.journalPosting = journalPostingResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJournalPostings() {
        this.eventSubscriber = this.eventManager.subscribe(
            'journalPostingListModification',
            (response) => this.load(this.journalPosting.id)
        );
    }
}
