/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { ChartOfAccountsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts-delete-dialog.component';
import { ChartOfAccountsService } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.service';

describe('Component Tests', () => {

    describe('ChartOfAccounts Management Delete Component', () => {
        let comp: ChartOfAccountsDeleteDialogComponent;
        let fixture: ComponentFixture<ChartOfAccountsDeleteDialogComponent>;
        let service: ChartOfAccountsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [ChartOfAccountsDeleteDialogComponent],
                providers: [
                    ChartOfAccountsService
                ]
            })
            .overrideTemplate(ChartOfAccountsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChartOfAccountsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChartOfAccountsService);
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
