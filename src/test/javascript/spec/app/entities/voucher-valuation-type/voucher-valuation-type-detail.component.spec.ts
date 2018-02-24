/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationTypeDetailComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type-detail.component';
import { VoucherValuationTypeService } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.service';
import { VoucherValuationType } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.model';

describe('Component Tests', () => {

    describe('VoucherValuationType Management Detail Component', () => {
        let comp: VoucherValuationTypeDetailComponent;
        let fixture: ComponentFixture<VoucherValuationTypeDetailComponent>;
        let service: VoucherValuationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationTypeDetailComponent],
                providers: [
                    VoucherValuationTypeService
                ]
            })
            .overrideTemplate(VoucherValuationTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VoucherValuationType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voucherValuationType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
