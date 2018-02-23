/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { Pratiche01TestModule } from '../../../test.module';
import { DocStoreDetailComponent } from '../../../../../../main/webapp/app/entities/doc-store/doc-store-detail.component';
import { DocStoreService } from '../../../../../../main/webapp/app/entities/doc-store/doc-store.service';
import { DocStore } from '../../../../../../main/webapp/app/entities/doc-store/doc-store.model';

describe('Component Tests', () => {

    describe('DocStore Management Detail Component', () => {
        let comp: DocStoreDetailComponent;
        let fixture: ComponentFixture<DocStoreDetailComponent>;
        let service: DocStoreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Pratiche01TestModule],
                declarations: [DocStoreDetailComponent],
                providers: [
                    DocStoreService
                ]
            })
            .overrideTemplate(DocStoreDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DocStoreDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocStoreService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DocStore(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.docStore).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
