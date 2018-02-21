/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { FinancialInstrumentTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type-delete-dialog.component';
import { FinancialInstrumentTypeService } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.service';

describe('Component Tests', () => {

    describe('FinancialInstrumentType Management Delete Component', () => {
        let comp: FinancialInstrumentTypeDeleteDialogComponent;
        let fixture: ComponentFixture<FinancialInstrumentTypeDeleteDialogComponent>;
        let service: FinancialInstrumentTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [FinancialInstrumentTypeDeleteDialogComponent],
                providers: [
                    FinancialInstrumentTypeService
                ]
            })
            .overrideTemplate(FinancialInstrumentTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialInstrumentTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialInstrumentTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
