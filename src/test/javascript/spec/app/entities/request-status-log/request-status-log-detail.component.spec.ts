/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestStatusLogDetailComponent } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log-detail.component';
import { RequestStatusLogService } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.service';
import { RequestStatusLog } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.model';

describe('Component Tests', () => {

    describe('RequestStatusLog Management Detail Component', () => {
        let comp: RequestStatusLogDetailComponent;
        let fixture: ComponentFixture<RequestStatusLogDetailComponent>;
        let service: RequestStatusLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestStatusLogDetailComponent],
                providers: [
                    RequestStatusLogService
                ]
            })
            .overrideTemplate(RequestStatusLogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestStatusLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestStatusLogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestStatusLog(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestStatusLog).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
