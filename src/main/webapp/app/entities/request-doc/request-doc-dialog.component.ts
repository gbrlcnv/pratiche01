import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestDoc } from './request-doc.model';
import { RequestDocPopupService } from './request-doc-popup.service';
import { RequestDocService } from './request-doc.service';
import { Request, RequestService } from '../request';
import { DocStore, DocStoreService } from '../doc-store';

@Component({
    selector: 'jhi-request-doc-dialog',
    templateUrl: './request-doc-dialog.component.html'
})
export class RequestDocDialogComponent implements OnInit {

    requestDoc: RequestDoc;
    isSaving: boolean;

    requests: Request[];

    docs: DocStore[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestDocService: RequestDocService,
        private requestService: RequestService,
        private docStoreService: DocStoreService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.requestService.query()
            .subscribe((res: HttpResponse<Request[]>) => { this.requests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.docStoreService
            .query({filter: 'requestdoc-is-null'})
            .subscribe((res: HttpResponse<DocStore[]>) => {
                if (!this.requestDoc.doc || !this.requestDoc.doc.id) {
                    this.docs = res.body;
                } else {
                    this.docStoreService
                        .find(this.requestDoc.doc.id)
                        .subscribe((subRes: HttpResponse<DocStore>) => {
                            this.docs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestDoc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestDocService.update(this.requestDoc));
        } else {
            this.subscribeToSaveResponse(
                this.requestDocService.create(this.requestDoc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestDoc>>) {
        result.subscribe((res: HttpResponse<RequestDoc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestDoc) {
        this.eventManager.broadcast({ name: 'requestDocListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRequestById(index: number, item: Request) {
        return item.id;
    }

    trackDocStoreById(index: number, item: DocStore) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-request-doc-popup',
    template: ''
})
export class RequestDocPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestDocPopupService: RequestDocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestDocPopupService
                    .open(RequestDocDialogComponent as Component, params['id']);
            } else {
                this.requestDocPopupService
                    .open(RequestDocDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
