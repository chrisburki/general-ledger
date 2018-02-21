/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { SubLedgerTypeDialogComponent } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type-dialog.component';
import { SubLedgerTypeService } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.service';
import { SubLedgerType } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.model';

describe('Component Tests', () => {

    describe('SubLedgerType Management Dialog Component', () => {
        let comp: SubLedgerTypeDialogComponent;
        let fixture: ComponentFixture<SubLedgerTypeDialogComponent>;
        let service: SubLedgerTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [SubLedgerTypeDialogComponent],
                providers: [
                    SubLedgerTypeService
                ]
            })
            .overrideTemplate(SubLedgerTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubLedgerTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubLedgerTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SubLedgerType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.subLedgerType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'subLedgerTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SubLedgerType();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.subLedgerType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'subLedgerTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
