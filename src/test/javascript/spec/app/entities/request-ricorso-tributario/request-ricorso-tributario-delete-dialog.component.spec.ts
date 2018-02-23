/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestRicorsoTributarioDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario-delete-dialog.component';
import { RequestRicorsoTributarioService } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.service';

describe('Component Tests', () => {

    describe('RequestRicorsoTributario Management Delete Component', () => {
        let comp: RequestRicorsoTributarioDeleteDialogComponent;
        let fixture: ComponentFixture<RequestRicorsoTributarioDeleteDialogComponent>;
        let service: RequestRicorsoTributarioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestRicorsoTributarioDeleteDialogComponent],
                providers: [
                    RequestRicorsoTributarioService
                ]
            })
            .overrideTemplate(RequestRicorsoTributarioDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestRicorsoTributarioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestRicorsoTributarioService);
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
