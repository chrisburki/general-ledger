/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LedgerTestModule } from '../../../test.module';
import { JournalPostingComponent } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.component';
import { JournalPostingService } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.service';
import { JournalPosting } from '../../../../../../main/webapp/app/entities/journal-posting/journal-posting.model';

describe('Component Tests', () => {

    describe('JournalPosting Management Component', () => {
        let comp: JournalPostingComponent;
        let fixture: ComponentFixture<JournalPostingComponent>;
        let service: JournalPostingService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LedgerTestModule],
                declarations: [JournalPostingComponent],
                providers: [
                    JournalPostingService
                ]
            })
            .overrideTemplate(JournalPostingComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(JournalPostingComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JournalPostingService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new JournalPosting(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.journalPostings[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
