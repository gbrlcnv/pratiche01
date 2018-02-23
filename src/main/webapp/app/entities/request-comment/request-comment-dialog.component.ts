import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestComment } from './request-comment.model';
import { RequestCommentPopupService } from './request-comment-popup.service';
import { RequestCommentService } from './request-comment.service';
import { Request, RequestService } from '../request';

@Component({
    selector: 'jhi-request-comment-dialog',
    templateUrl: './request-comment-dialog.component.html'
})
export class RequestCommentDialogComponent implements OnInit {

    requestComment: RequestComment;
    isSaving: boolean;

    requests: Request[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestCommentService: RequestCommentService,
        private requestService: RequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.requestService.query()
            .subscribe((res: HttpResponse<Request[]>) => { this.requests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestComment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestCommentService.update(this.requestComment));
        } else {
            this.subscribeToSaveResponse(
                this.requestCommentService.create(this.requestComment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestComment>>) {
        result.subscribe((res: HttpResponse<RequestComment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestComment) {
        this.eventManager.broadcast({ name: 'requestCommentListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-request-comment-popup',
    template: ''
})
export class RequestCommentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestCommentPopupService: RequestCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestCommentPopupService
                    .open(RequestCommentDialogComponent as Component, params['id']);
            } else {
                this.requestCommentPopupService
                    .open(RequestCommentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
