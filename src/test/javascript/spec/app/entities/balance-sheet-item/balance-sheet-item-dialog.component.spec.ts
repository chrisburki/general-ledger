/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetItemDialogComponent } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item-dialog.component';
import { BalanceSheetItemService } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.service';
import { BalanceSheetItem } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.model';
import { BalanceSheetService } from '../../../../../../main/webapp/app/entities/balance-sheet';
import { LedgerAccountService } from '../../../../../../main/webapp/app/entities/ledger-account';

describe('Component Tests', () => {

    describe('BalanceSheetItem Management Dialog Component', () => {
        let comp: BalanceSheetItemDialogComponent;
        let fixture: ComponentFixture<BalanceSheetItemDialogComponent>;
        let service: BalanceSheetItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetItemDialogComponent],
                providers: [
                    BalanceSheetService,
                    LedgerAccountService,
                    BalanceSheetItemService
                ]
            })
            .overrideTemplate(BalanceSheetItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceSheetItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceSheetItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceSheetItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceSheetItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceSheetItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceSheetItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
