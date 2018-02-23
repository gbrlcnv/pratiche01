/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestStatusLogDialogComponent } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log-dialog.component';
import { RequestStatusLogService } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.service';
import { RequestStatusLog } from '../../../../../../main/webapp/app/entities/request-status-log/request-status-log.model';
import { RequestService } from '../../../../../../main/webapp/app/entities/request';
import { RequestStatusService } from '../../../../../../main/webapp/app/entities/request-status';

describe('Component Tests', () => {

    describe('RequestStatusLog Management Dialog Component', () => {
        let comp: RequestStatusLogDialogComponent;
        let fixture: ComponentFixture<RequestStatusLogDialogComponent>;
        let service: RequestStatusLogService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestStatusLogDialogComponent],
                providers: [
                    RequestService,
                    RequestStatusService,
                    RequestStatusLogService
                ]
            })
            .overrideTemplate(RequestStatusLogDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestStatusLogDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestStatusLogService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RequestStatusLog(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.requestStatusLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'requestStatusLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RequestStatusLog();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.requestStatusLog = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'requestStatusLogListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
