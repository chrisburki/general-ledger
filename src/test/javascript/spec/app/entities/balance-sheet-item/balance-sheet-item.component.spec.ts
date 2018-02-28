/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetItemComponent } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.component';
import { BalanceSheetItemService } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.service';
import { BalanceSheetItem } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.model';

describe('Component Tests', () => {

    describe('BalanceSheetItem Management Component', () => {
        let comp: BalanceSheetItemComponent;
        let fixture: ComponentFixture<BalanceSheetItemComponent>;
        let service: BalanceSheetItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetItemComponent],
                providers: [
                    BalanceSheetItemService
                ]
            })
            .overrideTemplate(BalanceSheetItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BalanceSheetItem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.balanceSheetItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
