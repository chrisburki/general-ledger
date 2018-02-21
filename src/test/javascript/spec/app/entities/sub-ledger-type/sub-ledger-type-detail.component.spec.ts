/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { SubLedgerTypeDetailComponent } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type-detail.component';
import { SubLedgerTypeService } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.service';
import { SubLedgerType } from '../../../../../../main/webapp/app/entities/sub-ledger-type/sub-ledger-type.model';

describe('Component Tests', () => {

    describe('SubLedgerType Management Detail Component', () => {
        let comp: SubLedgerTypeDetailComponent;
        let fixture: ComponentFixture<SubLedgerTypeDetailComponent>;
        let service: SubLedgerTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [SubLedgerTypeDetailComponent],
                providers: [
                    SubLedgerTypeService
                ]
            })
            .overrideTemplate(SubLedgerTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubLedgerTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubLedgerTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SubLedgerType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.subLedgerType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
