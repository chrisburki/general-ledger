/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { LedgerAccountComponent } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account.component';
import { LedgerAccountService } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account.service';
import { LedgerAccount } from '../../../../../../main/webapp/app/entities/ledger-account/ledger-account.model';

describe('Component Tests', () => {

    describe('LedgerAccount Management Component', () => {
        let comp: LedgerAccountComponent;
        let fixture: ComponentFixture<LedgerAccountComponent>;
        let service: LedgerAccountService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [LedgerAccountComponent],
                providers: [
                    LedgerAccountService
                ]
            })
            .overrideTemplate(LedgerAccountComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LedgerAccountComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LedgerAccountService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LedgerAccount(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ledgerAccounts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
