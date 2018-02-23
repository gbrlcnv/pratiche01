/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestDocDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/request-doc/request-doc-delete-dialog.component';
import { RequestDocService } from '../../../../../../main/webapp/app/entities/request-doc/request-doc.service';

describe('Component Tests', () => {

    describe('RequestDoc Management Delete Component', () => {
        let comp: RequestDocDeleteDialogComponent;
        let fixture: ComponentFixture<RequestDocDeleteDialogComponent>;
        let service: RequestDocService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestDocDeleteDialogComponent],
                providers: [
                    RequestDocService
                ]
            })
            .overrideTemplate(RequestDocDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestDocDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestDocService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
