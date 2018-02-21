/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { SubLedgerTypeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type-delete-dialog.component';
import { SubLedgerTypeService } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.service';

describe('Component Tests', () => {

    describe('SubLedgerType Management Delete Component', () => {
        let comp: SubLedgerTypeDeleteDialogComponent;
        let fixture: ComponentFixture<SubLedgerTypeDeleteDialogComponent>;
        let service: SubLedgerTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [SubLedgerTypeDeleteDialogComponent],
                providers: [
                    SubLedgerTypeService
                ]
            })
            .overrideTemplate(SubLedgerTypeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubLedgerTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubLedgerTypeService);
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
