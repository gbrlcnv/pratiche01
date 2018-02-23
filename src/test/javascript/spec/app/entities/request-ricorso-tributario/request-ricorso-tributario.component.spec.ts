/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { RequestRicorsoTributarioComponent } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.component';
import { RequestRicorsoTributarioService } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.service';
import { RequestRicorsoTributario } from '../../../../../../main/webapp/app/entities/request-ricorso-tributario/request-ricorso-tributario.model';

describe('Component Tests', () => {

    describe('RequestRicorsoTributario Management Component', () => {
        let comp: RequestRicorsoTributarioComponent;
        let fixture: ComponentFixture<RequestRicorsoTributarioComponent>;
        let service: RequestRicorsoTributarioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [RequestRicorsoTributarioComponent],
                providers: [
                    RequestRicorsoTributarioService
                ]
            })
            .overrideTemplate(RequestRicorsoTributarioComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestRicorsoTributarioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestRicorsoTributarioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new RequestRicorsoTributario(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.requestRicorsoTributarios[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
