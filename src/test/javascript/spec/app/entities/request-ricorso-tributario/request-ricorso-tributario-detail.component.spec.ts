/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestRicorsoTributarioDetailComponent } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario-detail.component';
import { RequestRicorsoTributarioService } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.service';
import { RequestRicorsoTributario } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.model';

describe('Component Tests', () => {

    describe('RequestRicorsoTributario Management Detail Component', () => {
        let comp: RequestRicorsoTributarioDetailComponent;
        let fixture: ComponentFixture<RequestRicorsoTributarioDetailComponent>;
        let service: RequestRicorsoTributarioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestRicorsoTributarioDetailComponent],
                providers: [
                    RequestRicorsoTributarioService
                ]
            })
            .overrideTemplate(RequestRicorsoTributarioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestRicorsoTributarioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestRicorsoTributarioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new RequestRicorsoTributario(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.requestRicorsoTributario).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
