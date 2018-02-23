/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestStatusLogComponent } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.component';
import { RequestStatusLogService } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.service';
import { RequestStatusLog } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.model';

describe('Component Tests', () => {

    describe('RequestStatusLog Management Component', () => {
        let comp: RequestStatusLogComponent;
        let fixture: ComponentFixture<RequestStatusLogComponent>;
        let service: RequestStatusLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestStatusLogComponent],
                providers: [
                    RequestStatusLogService
                ]
            })
            .overrideTemplate(RequestStatusLogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestStatusLogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestStatusLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestStatusLog(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestStatusLogs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
