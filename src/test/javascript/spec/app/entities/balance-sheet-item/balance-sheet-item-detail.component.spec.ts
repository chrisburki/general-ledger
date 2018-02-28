/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { BalanceSheetItemDetailComponent } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item-detail.component';
import { BalanceSheetItemService } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.service';
import { BalanceSheetItem } from '../../../../../../main/webapp/app/entities/balance-sheet-item/balance-sheet-item.model';

describe('Component Tests', () => {

    describe('BalanceSheetItem Management Detail Component', () => {
        let comp: BalanceSheetItemDetailComponent;
        let fixture: ComponentFixture<BalanceSheetItemDetailComponent>;
        let service: BalanceSheetItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [BalanceSheetItemDetailComponent],
                providers: [
                    BalanceSheetItemService
                ]
            })
            .overrideTemplate(BalanceSheetItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BalanceSheetItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BalanceSheetItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BalanceSheetItem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.balanceSheetItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
