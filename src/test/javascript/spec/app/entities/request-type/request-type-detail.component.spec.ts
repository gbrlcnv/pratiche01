/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestTypeDetailComponent } from '../../../../../../main/webapp/app/entities/request-type/request-type-detail.component';
import { RequestTypeService } from '../../../../../../main/webapp/app/entities/request-type/request-type.service';
import { RequestType } from '../../../../../../main/webapp/app/entities/request-type/request-type.model';

describe('Component Tests', () => {

    describe('RequestType Management Detail Component', () => {
        let comp: RequestTypeDetailComponent;
        let fixture: ComponentFixture<RequestTypeDetailComponent>;
        let service: RequestTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestTypeDetailComponent],
                providers: [
                    RequestTypeService
                ]
            })
            .overrideTemplate(RequestTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
