/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationTypeComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.component';
import { VoucherValuationTypeService } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.service';
import { VoucherValuationType } from '../../../../../../main/webapp/app/entities/voucher-valuation-type/voucher-valuation-type.model';

describe('Component Tests', () => {

    describe('VoucherValuationType Management Component', () => {
        let comp: VoucherValuationTypeComponent;
        let fixture: ComponentFixture<VoucherValuationTypeComponent>;
        let service: VoucherValuationTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationTypeComponent],
                providers: [
                    VoucherValuationTypeService
                ]
            })
            .overrideTemplate(VoucherValuationTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VoucherValuationType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voucherValuationTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
