import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestStatus } from './request-status.model';
import { RequestStatusPopupService } from './request-status-popup.service';
import { RequestStatusService } from './request-status.service';

@Component({
    selector: 'jhi-request-status-dialog',
    templateUrl: './request-status-dialog.component.html'
})
export class RequestStatusDialogComponent implements OnInit {

    requestStatus: RequestStatus;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private requestStatusService: RequestStatusService,
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
        if (this.requestStatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestStatusService.update(this.requestStatus));
        } else {
            this.subscribeToSaveResponse(
                this.requestStatusService.create(this.requestStatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestStatus>>) {
        result.subscribe((res: HttpResponse<RequestStatus>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestStatus) {
        this.eventManager.broadcast({ name: 'requestStatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-request-status-popup',
    template: ''
})
export class RequestStatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestStatusPopupService: RequestStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestStatusPopupService
                    .open(RequestStatusDialogComponent as Component, params['id']);
            } else {
                this.requestStatusPopupService
                    .open(RequestStatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
