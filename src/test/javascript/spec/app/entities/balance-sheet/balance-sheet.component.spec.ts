/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetComponent } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.component';
import { BalanceSheetService } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.service';
import { BalanceSheet } from '../../../../../../main/webapp/app/entities/balance-sheet/balance-sheet.model';

describe('Component Tests', () => {

    describe('BalanceSheet Management Component', () => {
        let comp: BalanceSheetComponent;
        let fixture: ComponentFixture<BalanceSheetComponent>;
        let service: BalanceSheetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetComponent],
                providers: [
                    BalanceSheetService
                ]
            })
            .overrideTemplate(BalanceSheetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BalanceSheet(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.balanceSheets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
