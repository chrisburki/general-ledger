/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetDialogComponent } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet-dialog.component';
import { BalanceSheetService } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.service';
import { BalanceSheet } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.model';
import { ChartOfAccountsService } from '../../../../../../main/webapp/app/entities/chart-of-accounts';
import { LedgerAccountService } from '../../../../../../main/webapp/app/entities/ledger-account';

describe('Component Tests', () => {

    describe('BalanceSheet Management Dialog Component', () => {
        let comp: BalanceSheetDialogComponent;
        let fixture: ComponentFixture<BalanceSheetDialogComponent>;
        let service: BalanceSheetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetDialogComponent],
                providers: [
                    ChartOfAccountsService,
                    LedgerAccountService,
                    BalanceSheetService
                ]
            })
            .overrideTemplate(BalanceSheetDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceSheet(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceSheet = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceSheetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BalanceSheet();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.balanceSheet = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'balanceSheetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
