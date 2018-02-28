/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetItemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item-delete-dialog.component';
import { BalanceSheetItemService } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.service';

describe('Component Tests', () => {

    describe('BalanceSheetItem Management Delete Component', () => {
        let comp: BalanceSheetItemDeleteDialogComponent;
        let fixture: ComponentFixture<BalanceSheetItemDeleteDialogComponent>;
        let service: BalanceSheetItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetItemDeleteDialogComponent],
                providers: [
                    BalanceSheetItemService
                ]
            })
            .overrideTemplate(BalanceSheetItemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetItemService);
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
