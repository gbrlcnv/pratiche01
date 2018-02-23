import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestStatusLog } from './request-status-log.model';
import { RequestStatusLogPopupService } from './request-status-log-popup.service';
import { RequestStatusLogService } from './request-status-log.service';
import { Request, RequestService } from '../request';
import { RequestStatus, RequestStatusService } from '../request-status';

@Component({
    selector: 'jhi-request-status-log-dialog',
    templateUrl: './request-status-log-dialog.component.html'
})
export class RequestStatusLogDialogComponent implements OnInit {

    requestStatusLog: RequestStatusLog;
    isSaving: boolean;

    requests: Request[];

    requeststatuses: RequestStatus[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestStatusLogService: RequestStatusLogService,
        private requestService: RequestService,
        private requestStatusService: RequestStatusService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.requestService.query()
            .subscribe((res: HttpResponse<Request[]>) => { this.requests = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.requestStatusService.query()
            .subscribe((res: HttpResponse<RequestStatus[]>) => { this.requeststatuses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestStatusLog.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestStatusLogService.update(this.requestStatusLog));
        } else {
            this.subscribeToSaveResponse(
                this.requestStatusLogService.create(this.requestStatusLog));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestStatusLog>>) {
        result.subscribe((res: HttpResponse<RequestStatusLog>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestStatusLog) {
        this.eventManager.broadcast({ name: 'requestStatusLogListModification', content: 'OK'});
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

    trackRequestStatusById(index: number, item: RequestStatus) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-request-status-log-popup',
    template: ''
})
export class RequestStatusLogPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestStatusLogPopupService: RequestStatusLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestStatusLogPopupService
                    .open(RequestStatusLogDialogComponent as Component, params['id']);
            } else {
                this.requestStatusLogPopupService
                    .open(RequestStatusLogDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
