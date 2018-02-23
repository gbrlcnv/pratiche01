import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestStatus } from './request-status.model';
import { RequestStatusService } from './request-status.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-status',
    templateUrl: './request-status.component.html'
})
export class RequestStatusComponent implements OnInit, OnDestroy {
requestStatuses: RequestStatus[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestStatusService: RequestStatusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestStatusService.query().subscribe(
            (res: HttpResponse<RequestStatus[]>) => {
                this.requestStatuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestStatuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestStatus) {
        return item.id;
    }
    registerChangeInRequestStatuses() {
        this.eventSubscriber = this.eventManager.subscribe('requestStatusListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
