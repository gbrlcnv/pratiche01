/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestRicorsoTributarioDialogComponent } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario-dialog.component';
import { RequestRicorsoTributarioService } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.service';
import { RequestRicorsoTributario } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.model';
import { RequestService } from '../../../../../../main/webapp/app/entities/request';

describe('Component Tests', () => {

    describe('RequestRicorsoTributario Management Dialog Component', () => {
        let comp: RequestRicorsoTributarioDialogComponent;
        let fixture: ComponentFixture<RequestRicorsoTributarioDialogComponent>;
        let service: RequestRicorsoTributarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestRicorsoTributarioDialogComponent],
                providers: [
                    RequestService,
                    RequestRicorsoTributarioService
                ]
            })
            .overrideTemplate(RequestRicorsoTributarioDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestRicorsoTributarioDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestRicorsoTributarioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RequestRicorsoTributario(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.requestRicorsoTributario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'requestRicorsoTributarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new RequestRicorsoTributario();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.requestRicorsoTributario = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'requestRicorsoTributarioListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
