import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { JournalPosting } from './journal-posting.model';
import { JournalPostingService } from './journal-posting.service';

@Injectable()
export class JournalPostingPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private journalPostingService: JournalPostingService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.journalPostingService.find(id)
                    .subscribe((journalPostingResponse: HttpResponse<JournalPosting>) => {
                        const journalPosting: JournalPosting = journalPostingResponse.body;
                        if (journalPosting.bookDate) {
                            journalPosting.bookDate = {
                                year: journalPosting.bookDate.getFullYear(),
                                month: journalPosting.bookDate.getMonth() + 1,
                                day: journalPosting.bookDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.journalPostingModalRef(component, journalPosting);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.journalPostingModalRef(component, new JournalPosting());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    journalPostingModalRef(component: Component, journalPosting: JournalPosting): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.journalPosting = journalPosting;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
