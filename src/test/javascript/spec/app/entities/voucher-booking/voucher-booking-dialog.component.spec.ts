/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherBookingDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking-dialog.component';
import { VoucherBookingService } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.service';
import { VoucherBooking } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.model';
import { JournalPostingService } from '../../../../../../main/webapp/app/entities/journal-posting';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position';

describe('Component Tests', () => {

    describe('VoucherBooking Management Dialog Component', () => {
        let comp: VoucherBookingDialogComponent;
        let fixture: ComponentFixture<VoucherBookingDialogComponent>;
        let service: VoucherBookingService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherBookingDialogComponent],
                providers: [
                    JournalPostingService,
                    VoucherPositionService,
                    VoucherBookingService
                ]
            })
            .overrideTemplate(VoucherBookingDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherBookingDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherBookingService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherBooking(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherBooking = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherBookingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherBooking();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherBooking = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherBookingListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
