/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { FinancialInstrumentTypeDetailComponent } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type-detail.component';
import { FinancialInstrumentTypeService } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.service';
import { FinancialInstrumentType } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.model';

describe('Component Tests', () => {

    describe('FinancialInstrumentType Management Detail Component', () => {
        let comp: FinancialInstrumentTypeDetailComponent;
        let fixture: ComponentFixture<FinancialInstrumentTypeDetailComponent>;
        let service: FinancialInstrumentTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [FinancialInstrumentTypeDetailComponent],
                providers: [
                    FinancialInstrumentTypeService
                ]
            })
            .overrideTemplate(FinancialInstrumentTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialInstrumentTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialInstrumentTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FinancialInstrumentType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.financialInstrumentType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
