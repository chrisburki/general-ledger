/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { VoucherBookingComponent } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.component';
import { VoucherBookingService } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.service';
import { VoucherBooking } from '../../../../../../main/webapp/app/entities/voucher-booking/voucher-booking.model';

describe('Component Tests', () => {

    describe('VoucherBooking Management Component', () => {
        let comp: VoucherBookingComponent;
        let fixture: ComponentFixture<VoucherBookingComponent>;
        let service: VoucherBookingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherBookingComponent],
                providers: [
                    VoucherBookingService
                ]
            })
            .overrideTemplate(VoucherBookingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherBookingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherBookingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VoucherBooking(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voucherBookings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
