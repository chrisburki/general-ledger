/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherPositionDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position-dialog.component';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.service';
import { VoucherPosition } from '../../../../../../main/webapp/app/entities/voucher-position/voucher-position.model';
import { FinancialInstrumentTypeService } from '../../../../../../main/webapp/app/entities/financial-instrument-type';
import { VoucherAccountTypeService } from '../../../../../../main/webapp/app/entities/voucher-account-type';
import { SubLedgerTypeService } from '../../../../../../main/webapp/app/entities/sub-ledger-type';

describe('Component Tests', () => {

    describe('VoucherPosition Management Dialog Component', () => {
        let comp: VoucherPositionDialogComponent;
        let fixture: ComponentFixture<VoucherPositionDialogComponent>;
        let service: VoucherPositionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherPositionDialogComponent],
                providers: [
                    FinancialInstrumentTypeService,
                    VoucherAccountTypeService,
                    SubLedgerTypeService,
                    VoucherPositionService
                ]
            })
            .overrideTemplate(VoucherPositionDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherPositionDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherPositionService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherPosition(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherPosition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherPositionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherPosition();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherPosition = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherPositionListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
