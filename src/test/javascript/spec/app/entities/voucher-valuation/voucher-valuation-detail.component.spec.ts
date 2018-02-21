/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationDetailComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation-detail.component';
import { VoucherValuationService } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.service';
import { VoucherValuation } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.model';

describe('Component Tests', () => {

    describe('VoucherValuation Management Detail Component', () => {
        let comp: VoucherValuationDetailComponent;
        let fixture: ComponentFixture<VoucherValuationDetailComponent>;
        let service: VoucherValuationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationDetailComponent],
                providers: [
                    VoucherValuationService
                ]
            })
            .overrideTemplate(VoucherValuationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VoucherValuation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voucherValuation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
