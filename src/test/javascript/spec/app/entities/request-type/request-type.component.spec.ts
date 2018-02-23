/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestTypeComponent } from '../../../../../../main/webapp/app/entities/request-type/request-type.component';
import { RequestTypeService } from '../../../../../../main/webapp/app/entities/request-type/request-type.service';
import { RequestType } from '../../../../../../main/webapp/app/entities/request-type/request-type.model';

describe('Component Tests', () => {

    describe('RequestType Management Component', () => {
        let comp: RequestTypeComponent;
        let fixture: ComponentFixture<RequestTypeComponent>;
        let service: RequestTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestTypeComponent],
                providers: [
                    RequestTypeService
                ]
            })
            .overrideTemplate(RequestTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
