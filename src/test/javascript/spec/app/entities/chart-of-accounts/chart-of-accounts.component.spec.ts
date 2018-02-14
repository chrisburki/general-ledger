/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { ChartOfAccountsComponent } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.component';
import { ChartOfAccountsService } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.service';
import { ChartOfAccounts } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.model';

describe('Component Tests', () => {

    describe('ChartOfAccounts Management Component', () => {
        let comp: ChartOfAccountsComponent;
        let fixture: ComponentFixture<ChartOfAccountsComponent>;
        let service: ChartOfAccountsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [ChartOfAccountsComponent],
                providers: [
                    ChartOfAccountsService
                ]
            })
            .overrideTemplate(ChartOfAccountsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChartOfAccountsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChartOfAccountsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ChartOfAccounts(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.chartOfAccounts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
