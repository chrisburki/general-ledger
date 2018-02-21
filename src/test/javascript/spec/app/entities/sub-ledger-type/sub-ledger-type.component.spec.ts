/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { SubLedgerTypeComponent } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.component';
import { SubLedgerTypeService } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.service';
import { SubLedgerType } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.model';

describe('Component Tests', () => {

    describe('SubLedgerType Management Component', () => {
        let comp: SubLedgerTypeComponent;
        let fixture: ComponentFixture<SubLedgerTypeComponent>;
        let service: SubLedgerTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [SubLedgerTypeComponent],
                providers: [
                    SubLedgerTypeService
                ]
            })
            .overrideTemplate(SubLedgerTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubLedgerTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubLedgerTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SubLedgerType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.subLedgerTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
