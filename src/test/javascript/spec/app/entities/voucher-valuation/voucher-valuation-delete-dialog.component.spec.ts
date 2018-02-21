/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation-delete-dialog.component';
import { VoucherValuationService } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.service';

describe('Component Tests', () => {

    describe('VoucherValuation Management Delete Component', () => {
        let comp: VoucherValuationDeleteDialogComponent;
        let fixture: ComponentFixture<VoucherValuationDeleteDialogComponent>;
        let service: VoucherValuationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationDeleteDialogComponent],
                providers: [
                    VoucherValuationService
                ]
            })
            .overrideTemplate(VoucherValuationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationService);
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
