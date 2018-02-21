/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { FinancialInstrumentTypeComponent } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.component';
import { FinancialInstrumentTypeService } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.service';
import { FinancialInstrumentType } from '../../../../../../main/webapp/app/entities/financial-instrument-type/financial-instrument-type.model';

describe('Component Tests', () => {

    describe('FinancialInstrumentType Management Component', () => {
        let comp: FinancialInstrumentTypeComponent;
        let fixture: ComponentFixture<FinancialInstrumentTypeComponent>;
        let service: FinancialInstrumentTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [FinancialInstrumentTypeComponent],
                providers: [
                    FinancialInstrumentTypeService
                ]
            })
            .overrideTemplate(FinancialInstrumentTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinancialInstrumentTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinancialInstrumentTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FinancialInstrumentType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.financialInstrumentTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
