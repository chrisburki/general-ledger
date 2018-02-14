/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { ChartOfAccountsDetailComponent } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts-detail.component';
import { ChartOfAccountsService } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.service';
import { ChartOfAccounts } from '../../../../../../main/webapp/app/entities/chart-of-accounts/chart-of-accounts.model';

describe('Component Tests', () => {

    describe('ChartOfAccounts Management Detail Component', () => {
        let comp: ChartOfAccountsDetailComponent;
        let fixture: ComponentFixture<ChartOfAccountsDetailComponent>;
        let service: ChartOfAccountsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [ChartOfAccountsDetailComponent],
                providers: [
                    ChartOfAccountsService
                ]
            })
            .overrideTemplate(ChartOfAccountsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChartOfAccountsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChartOfAccountsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ChartOfAccounts(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.chartOfAccounts).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
