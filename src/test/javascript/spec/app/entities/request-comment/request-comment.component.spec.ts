/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestCommentComponent } from '../../../../../../main/webapp/app/entities/request-comment/request-comment.component';
import { RequestCommentService } from '../../../../../../main/webapp/app/entities/request-comment/request-comment.service';
import { RequestComment } from '../../../../../../main/webapp/app/entities/request-comment/request-comment.model';

describe('Component Tests', () => {

    describe('RequestComment Management Component', () => {
        let comp: RequestCommentComponent;
        let fixture: ComponentFixture<RequestCommentComponent>;
        let service: RequestCommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestCommentComponent],
                providers: [
                    RequestCommentService
                ]
            })
            .overrideTemplate(RequestCommentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestCommentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestCommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestComment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestComments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
