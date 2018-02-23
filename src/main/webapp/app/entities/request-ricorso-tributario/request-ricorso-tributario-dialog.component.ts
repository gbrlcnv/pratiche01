import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioPopupService } from './request-ricorso-tributario-popup.service';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';
import { Request, RequestService } from '../request';

@Component({
    selector: 'jhi-request-ricorso-tributario-dialog',
    templateUrl: './request-ricorso-tributario-dialog.component.html'
})
export class RequestRicorsoTributarioDialogComponent implements OnInit {

    requestRicorsoTributario: RequestRicorsoTributario;
    isSaving: boolean;

    requests: Request[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private requestRicorsoTributarioService: RequestRicorsoTributarioService,
        private requestService: RequestService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.requestService
            .query({filter: 'requestricorsotributario-is-null'})
            .subscribe((res: HttpResponse<Request[]>) => {
                if (!this.requestRicorsoTributario.request || !this.requestRicorsoTributario.request.id) {
                    this.requests = res.body;
                } else {
                    this.requestService
                        .find(this.requestRicorsoTributario.request.id)
                        .subscribe((subRes: HttpResponse<Request>) => {
                            this.requests = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.requestRicorsoTributario.id !== undefined) {
            this.subscribeToSaveResponse(
                this.requestRicorsoTributarioService.update(this.requestRicorsoTributario));
        } else {
            this.subscribeToSaveResponse(
                this.requestRicorsoTributarioService.create(this.requestRicorsoTributario));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<RequestRicorsoTributario>>) {
        result.subscribe((res: HttpResponse<RequestRicorsoTributario>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: RequestRicorsoTributario) {
        this.eventManager.broadcast({ name: 'requestRicorsoTributarioListModification', content: 'OK'});
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
    selector: 'jhi-request-ricorso-tributario-popup',
    template: ''
})
export class RequestRicorsoTributarioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestRicorsoTributarioPopupService: RequestRicorsoTributarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.requestRicorsoTributarioPopupService
                    .open(RequestRicorsoTributarioDialogComponent as Component, params['id']);
            } else {
                this.requestRicorsoTributarioPopupService
                    .open(RequestRicorsoTributarioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
