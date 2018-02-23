/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestCommentDetailComponent } from '../../../../../../main/webapp/app/entities/request-comment/request-comment-detail.component';
import { RequestCommentService } from '../../../../../../main/webapp/app/entities/request-comment/request-comment.service';
import { RequestComment } from '../../../../../../main/webapp/app/entities/request-comment/request-comment.model';

describe('Component Tests', () => {

    describe('RequestComment Management Detail Component', () => {
        let comp: RequestCommentDetailComponent;
        let fixture: ComponentFixture<RequestCommentDetailComponent>;
        let service: RequestCommentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestCommentDetailComponent],
                providers: [
                    RequestCommentService
                ]
            })
            .overrideTemplate(RequestCommentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestCommentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestCommentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestComment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestComment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
