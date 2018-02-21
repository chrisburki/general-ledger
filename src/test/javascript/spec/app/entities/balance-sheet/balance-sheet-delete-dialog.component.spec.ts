/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet-delete-dialog.component';
import { BalanceSheetService } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.service';

describe('Component Tests', () => {

    describe('BalanceSheet Management Delete Component', () => {
        let comp: BalanceSheetDeleteDialogComponent;
        let fixture: ComponentFixture<BalanceSheetDeleteDialogComponent>;
        let service: BalanceSheetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetDeleteDialogComponent],
                providers: [
                    BalanceSheetService
                ]
            })
            .overrideTemplate(BalanceSheetDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
