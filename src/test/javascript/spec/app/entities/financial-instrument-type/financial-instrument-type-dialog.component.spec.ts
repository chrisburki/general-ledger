/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { FinancialInstrumentTypeDialogComponent } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type-dialog.component';
import { FinancialInstrumentTypeService } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.service';
import { FinancialInstrumentType } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.model';

describe('Component Tests', () => {

    describe('FinancialInstrumentType Management Dialog Component', () => {
        let comp: FinancialInstrumentTypeDialogComponent;
        let fixture: ComponentFixture<FinancialInstrumentTypeDialogComponent>;
        let service: FinancialInstrumentTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [FinancialInstrumentTypeDialogComponent],
                providers: [
                    FinancialInstrumentTypeService
                ]
            })
            .overrideTemplate(FinancialInstrumentTypeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialInstrumentTypeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialInstrumentTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinancialInstrumentType(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.financialInstrumentType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'financialInstrumentTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinancialInstrumentType();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.financialInstrumentType = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'financialInstrumentTypeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
