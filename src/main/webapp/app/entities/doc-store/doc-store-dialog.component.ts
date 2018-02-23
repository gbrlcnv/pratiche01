import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DocStore } from './doc-store.model';
import { DocStorePopupService } from './doc-store-popup.service';
import { DocStoreService } from './doc-store.service';

@Component({
    selector: 'jhi-doc-store-dialog',
    templateUrl: './doc-store-dialog.component.html'
})
export class DocStoreDialogComponent implements OnInit {

    docStore: DocStore;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private docStoreService: DocStoreService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.docStore, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.docStore.id !== undefined) {
            this.subscribeToSaveResponse(
                this.docStoreService.update(this.docStore));
        } else {
            this.subscribeToSaveResponse(
                this.docStoreService.create(this.docStore));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DocStore>>) {
        result.subscribe((res: HttpResponse<DocStore>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DocStore) {
        this.eventManager.broadcast({ name: 'docStoreListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-doc-store-popup',
    template: ''
})
export class DocStorePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private docStorePopupService: DocStorePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.docStorePopupService
                    .open(DocStoreDialogComponent as Component, params['id']);
            } else {
                this.docStorePopupService
                    .open(DocStoreDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
