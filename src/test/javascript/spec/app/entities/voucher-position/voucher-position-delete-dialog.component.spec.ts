/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherPositionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position-delete-dialog.component';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.service';

describe('Component Tests', () => {

    describe('VoucherPosition Management Delete Component', () => {
        let comp: VoucherPositionDeleteDialogComponent;
        let fixture: ComponentFixture<VoucherPositionDeleteDialogComponent>;
        let service: VoucherPositionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherPositionDeleteDialogComponent],
                providers: [
                    VoucherPositionService
                ]
            })
            .overrideTemplate(VoucherPositionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherPositionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherPositionService);
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
