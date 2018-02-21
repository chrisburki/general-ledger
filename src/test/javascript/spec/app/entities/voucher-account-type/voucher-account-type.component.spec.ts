/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { VoucherAccountTypeComponent } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.component';
import { VoucherAccountTypeService } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.service';
import { VoucherAccountType } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.model';

describe('Component Tests', () => {

    describe('VoucherAccountType Management Component', () => {
        let comp: VoucherAccountTypeComponent;
        let fixture: ComponentFixture<VoucherAccountTypeComponent>;
        let service: VoucherAccountTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherAccountTypeComponent],
                providers: [
                    VoucherAccountTypeService
                ]
            })
            .overrideTemplate(VoucherAccountTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherAccountTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherAccountTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VoucherAccountType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voucherAccountTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
