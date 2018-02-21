/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherAccountTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type-delete-dialog.component';
import { VoucherAccountTypeService } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.service';

describe('Component Tests', () => {

    describe('VoucherAccountType Management Delete Component', () => {
        let comp: VoucherAccountTypeDeleteDialogComponent;
        let fixture: ComponentFixture<VoucherAccountTypeDeleteDialogComponent>;
        let service: VoucherAccountTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherAccountTypeDeleteDialogComponent],
                providers: [
                    VoucherAccountTypeService
                ]
            })
            .overrideTemplate(VoucherAccountTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherAccountTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherAccountTypeService);
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
