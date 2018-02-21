/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { VoucherValuationDialogComponent } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation-dialog.component';
import { VoucherValuationService } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.service';
import { VoucherValuation } from '../../../../../../main/webapp/app/entities/voucher-valuation/voucher-valuation.model';
import { JournalPostingService } from '../../../../../../main/webapp/app/entities/journal-posting';
import { VoucherPositionService } from '../../../../../../main/webapp/app/entities/voucher-position';

describe('Component Tests', () => {

    describe('VoucherValuation Management Dialog Component', () => {
        let comp: VoucherValuationDialogComponent;
        let fixture: ComponentFixture<VoucherValuationDialogComponent>;
        let service: VoucherValuationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [VoucherValuationDialogComponent],
                providers: [
                    JournalPostingService,
                    VoucherPositionService,
                    VoucherValuationService
                ]
            })
            .overrideTemplate(VoucherValuationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoucherValuationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoucherValuationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherValuation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherValuation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherValuationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoucherValuation();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.voucherValuation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voucherValuationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
