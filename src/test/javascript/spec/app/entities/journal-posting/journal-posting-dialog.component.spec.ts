/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { JournalPostingDialogComponent } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting-dialog.component';
import { JournalPostingService } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.service';
import { JournalPosting } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.model';
import { LedgerAccountService } from '../../../../../../main/webapp/app/entities/ledger-account';

describe('Component Tests', () => {

    describe('JournalPosting Management Dialog Component', () => {
        let comp: JournalPostingDialogComponent;
        let fixture: ComponentFixture<JournalPostingDialogComponent>;
        let service: JournalPostingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [JournalPostingDialogComponent],
                providers: [
                    LedgerAccountService,
                    JournalPostingService
                ]
            })
            .overrideTemplate(JournalPostingDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JournalPostingDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalPostingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JournalPosting(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.journalPosting = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'journalPostingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new JournalPosting();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.journalPosting = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'journalPostingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
