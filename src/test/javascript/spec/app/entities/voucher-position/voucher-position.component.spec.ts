/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { VoucherPositionComponent } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.component';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.service';
import { VoucherPosition } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.model';

describe('Component Tests', () => {

    describe('VoucherPosition Management Component', () => {
        let comp: VoucherPositionComponent;
        let fixture: ComponentFixture<VoucherPositionComponent>;
        let service: VoucherPositionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherPositionComponent],
                providers: [
                    VoucherPositionService
                ]
            })
            .overrideTemplate(VoucherPositionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherPositionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherPositionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new VoucherPosition(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voucherPositions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
