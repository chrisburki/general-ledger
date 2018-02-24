/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type-delete-dialog.component';
import { VoucherValuationTypeService } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.service';

describe('Component Tests', () => {

    describe('VoucherValuationType Management Delete Component', () => {
        let comp: VoucherValuationTypeDeleteDialogComponent;
        let fixture: ComponentFixture<VoucherValuationTypeDeleteDialogComponent>;
        let service: VoucherValuationTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationTypeDeleteDialogComponent],
                providers: [
                    VoucherValuationTypeService
                ]
            })
            .overrideTemplate(VoucherValuationTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationTypeService);
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
