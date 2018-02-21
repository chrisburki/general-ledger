/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetDetailComponent } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet-detail.component';
import { BalanceSheetService } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.service';
import { BalanceSheet } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.model';

describe('Component Tests', () => {

    describe('BalanceSheet Management Detail Component', () => {
        let comp: BalanceSheetDetailComponent;
        let fixture: ComponentFixture<BalanceSheetDetailComponent>;
        let service: BalanceSheetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetDetailComponent],
                providers: [
                    BalanceSheetService
                ]
            })
            .overrideTemplate(BalanceSheetDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BalanceSheet(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.balanceSheet).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
