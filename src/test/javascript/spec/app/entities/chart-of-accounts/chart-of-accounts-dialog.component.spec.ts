/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { LedgerTestModule } from '../../../test.module';
import { ChartOfAccountsDialogComponent } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts-dialog.component';
import { ChartOfAccountsService } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.service';
import { ChartOfAccounts } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.model';

describe('Component Tests', () => {

    describe('ChartOfAccounts Management Dialog Component', () => {
        let comp: ChartOfAccountsDialogComponent;
        let fixture: ComponentFixture<ChartOfAccountsDialogComponent>;
        let service: ChartOfAccountsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [ChartOfAccountsDialogComponent],
                providers: [
                    ChartOfAccountsService
                ]
            })
            .overrideTemplate(ChartOfAccountsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChartOfAccountsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChartOfAccountsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ChartOfAccounts(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chartOfAccounts = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chartOfAccountsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ChartOfAccounts();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chartOfAccounts = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chartOfAccountsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
