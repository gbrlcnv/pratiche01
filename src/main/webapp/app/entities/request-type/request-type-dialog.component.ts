import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestType } from './request-type.model';
import { RequestTypePopupService } from './request-type-popup.service';
import { RequestTypeService } from './request-type.service';

@Component({
    selector: 'jhi-request-type-dialog',
    templateUrl: './request-type-dialog.component.html'
})
export class RequestTypeDialogComponent implements OnInit {

    requestType: RequestType;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private requestTypeService: RequestTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestTypeService.update(this.requestType));
        } else {
            this.subscribeToSaveResponse(
                this.requestTypeService.create(this.requestType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestType>>) {
        result.subscribe((res: HttpResponse<RequestType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestType) {
        this.eventManager.broadcast({ name: 'requestTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-request-type-popup',
    template: ''
})
export class RequestTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestTypePopupService: RequestTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestTypePopupService
                    .open(RequestTypeDialogComponent as Component, params['id']);
            } else {
                this.requestTypePopupService
                    .open(RequestTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
