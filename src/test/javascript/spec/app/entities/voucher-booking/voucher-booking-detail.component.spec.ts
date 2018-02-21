/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { VoucherBookingDetailComponent } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking-detail.component';
import { VoucherBookingService } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.service';
import { VoucherBooking } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.model';

describe('Component Tests', () => {

    describe('VoucherBooking Management Detail Component', () => {
        let comp: VoucherBookingDetailComponent;
        let fixture: ComponentFixture<VoucherBookingDetailComponent>;
        let service: VoucherBookingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherBookingDetailComponent],
                providers: [
                    VoucherBookingService
                ]
            })
            .overrideTemplate(VoucherBookingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherBookingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherBookingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VoucherBooking(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voucherBooking).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
