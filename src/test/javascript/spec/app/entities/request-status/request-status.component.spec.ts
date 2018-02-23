/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestStatusComponent } from '../../../../../../main/webapp/app/entities/request-status/request-status.component';
import { RequestStatusService } from '../../../../../../main/webapp/app/entities/request-status/request-status.service';
import { RequestStatus } from '../../../../../../main/webapp/app/entities/request-status/request-status.model';

describe('Component Tests', () => {

    describe('RequestStatus Management Component', () => {
        let comp: RequestStatusComponent;
        let fixture: ComponentFixture<RequestStatusComponent>;
        let service: RequestStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestStatusComponent],
                providers: [
                    RequestStatusService
                ]
            })
            .overrideTemplate(RequestStatusComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestStatusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestStatus(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestStatuses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
