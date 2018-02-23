import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestStatusLog } from './request-status-log.model';
import { RequestStatusLogService } from './request-status-log.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-status-log',
    templateUrl: './request-status-log.component.html'
})
export class RequestStatusLogComponent implements OnInit, OnDestroy {
requestStatusLogs: RequestStatusLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestStatusLogService: RequestStatusLogService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestStatusLogService.query().subscribe(
            (res: HttpResponse<RequestStatusLog[]>) => {
                this.requestStatusLogs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestStatusLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestStatusLog) {
        return item.id;
    }
    registerChangeInRequestStatusLogs() {
        this.eventSubscriber = this.eventManager.subscribe('requestStatusLogListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
