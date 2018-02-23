/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestDocDetailComponent } from '../../../../../../main/webapp/app/entities/request-doc/request-doc-detail.component';
import { RequestDocService } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.service';
import { RequestDoc } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.model';

describe('Component Tests', () => {

    describe('RequestDoc Management Detail Component', () => {
        let comp: RequestDocDetailComponent;
        let fixture: ComponentFixture<RequestDocDetailComponent>;
        let service: RequestDocService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestDocDetailComponent],
                providers: [
                    RequestDocService
                ]
            })
            .overrideTemplate(RequestDocDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestDocDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestDocService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestDoc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestDoc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
