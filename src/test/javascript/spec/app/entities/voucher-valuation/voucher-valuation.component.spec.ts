/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.component';
import { VoucherValuationService } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.service';
import { VoucherValuation } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.model';

describe('Component Tests', () => {

    describe('VoucherValuation Management Component', () => {
        let comp: VoucherValuationComponent;
        let fixture: ComponentFixture<VoucherValuationComponent>;
        let service: VoucherValuationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationComponent],
                providers: [
                    VoucherValuationService
                ]
            })
            .overrideTemplate(VoucherValuationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VoucherValuation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voucherValuations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
