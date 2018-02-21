/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { VoucherAccountTypeDetailComponent } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type-detail.component';
import { VoucherAccountTypeService } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.service';
import { VoucherAccountType } from '../../../../../../main/webapp/app/entities/voucher-account-type/voucher-account-type.model';

describe('Component Tests', () => {

    describe('VoucherAccountType Management Detail Component', () => {
        let comp: VoucherAccountTypeDetailComponent;
        let fixture: ComponentFixture<VoucherAccountTypeDetailComponent>;
        let service: VoucherAccountTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherAccountTypeDetailComponent],
                providers: [
                    VoucherAccountTypeService
                ]
            })
            .overrideTemplate(VoucherAccountTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherAccountTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherAccountTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VoucherAccountType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voucherAccountType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
