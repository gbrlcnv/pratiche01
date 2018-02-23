/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestDocComponent } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.component';
import { RequestDocService } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.service';
import { RequestDoc } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.model';

describe('Component Tests', () => {

    describe('RequestDoc Management Component', () => {
        let comp: RequestDocComponent;
        let fixture: ComponentFixture<RequestDocComponent>;
        let service: RequestDocService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestDocComponent],
                providers: [
                    RequestDocService
                ]
            })
            .overrideTemplate(RequestDocComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestDocComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestDocService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestDoc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestDocs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
