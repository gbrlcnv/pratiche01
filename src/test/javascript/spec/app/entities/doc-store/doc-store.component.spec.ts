/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Pratiche01TestModule } from '../../../test.module';
import { DocStoreComponent } from '../../../../../../main/webapp/app/entities/doc-store/doc-store.component';
import { DocStoreService } from '../../../../../../main/webapp/app/entities/doc-store/doc-store.service';
import { DocStore } from '../../../../../../main/webapp/app/entities/doc-store/doc-store.model';

describe('Component Tests', () => {

    describe('DocStore Management Component', () => {
        let comp: DocStoreComponent;
        let fixture: ComponentFixture<DocStoreComponent>;
        let service: DocStoreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [DocStoreComponent],
                providers: [
                    DocStoreService
                ]
            })
            .overrideTemplate(DocStoreComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocStoreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocStoreService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DocStore(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.docStores[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
