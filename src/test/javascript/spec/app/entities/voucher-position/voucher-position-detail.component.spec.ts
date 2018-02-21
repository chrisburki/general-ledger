/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { VoucherPositionDetailComponent } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position-detail.component';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.service';
import { VoucherPosition } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.model';

describe('Component Tests', () => {

    describe('VoucherPosition Management Detail Component', () => {
        let comp: VoucherPositionDetailComponent;
        let fixture: ComponentFixture<VoucherPositionDetailComponent>;
        let service: VoucherPositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherPositionDetailComponent],
                providers: [
                    VoucherPositionService
                ]
            })
            .overrideTemplate(VoucherPositionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherPositionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherPositionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new VoucherPosition(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voucherPosition).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
