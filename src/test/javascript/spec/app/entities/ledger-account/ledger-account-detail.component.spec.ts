/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { LedgerAccountDetailComponent } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account-detail.component';
import { LedgerAccountService } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account.service';
import { LedgerAccount } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account.model';

describe('Component Tests', () => {

    describe('LedgerAccount Management Detail Component', () => {
        let comp: LedgerAccountDetailComponent;
        let fixture: ComponentFixture<LedgerAccountDetailComponent>;
        let service: LedgerAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [LedgerAccountDetailComponent],
                providers: [
                    LedgerAccountService
                ]
            })
            .overrideTemplate(LedgerAccountDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LedgerAccountDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LedgerAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LedgerAccount(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ledgerAccount).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
