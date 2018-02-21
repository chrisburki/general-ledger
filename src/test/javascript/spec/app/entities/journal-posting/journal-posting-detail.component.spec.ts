/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { LedgerTestModule } from '../../../test.module';
import { JournalPostingDetailComponent } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting-detail.component';
import { JournalPostingService } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.service';
import { JournalPosting } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.model';

describe('Component Tests', () => {

    describe('JournalPosting Management Detail Component', () => {
        let comp: JournalPostingDetailComponent;
        let fixture: ComponentFixture<JournalPostingDetailComponent>;
        let service: JournalPostingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [JournalPostingDetailComponent],
                providers: [
                    JournalPostingService
                ]
            })
            .overrideTemplate(JournalPostingDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JournalPostingDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalPostingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new JournalPosting(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.journalPosting).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
