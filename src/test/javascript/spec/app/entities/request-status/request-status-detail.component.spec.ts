/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestStatusDetailComponent } from '../../../../../../main/webapp/app/entities/request-status/request-status-detail.component';
import { RequestStatusService } from '../../../../../../main/webapp/app/entities/request-status/request-status.service';
import { RequestStatus } from '../../../../../../main/webapp/app/entities/request-status/request-status.model';

describe('Component Tests', () => {

    describe('RequestStatus Management Detail Component', () => {
        let comp: RequestStatusDetailComponent;
        let fixture: ComponentFixture<RequestStatusDetailComponent>;
        let service: RequestStatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestStatusDetailComponent],
                providers: [
                    RequestStatusService
                ]
            })
            .overrideTemplate(RequestStatusDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestStatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestStatusService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestStatus(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestStatus).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
