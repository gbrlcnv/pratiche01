import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestStatusLog } from './request-status-log.model';
import { RequestStatusLogService } from './request-status-log.service';

@Component({
    selector: 'jhi-request-status-log-detail',
    templateUrl: './request-status-log-detail.component.html'
})
export class RequestStatusLogDetailComponent implements OnInit, OnDestroy {

    requestStatusLog: RequestStatusLog;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestStatusLogService: RequestStatusLogService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestStatusLogs();
    }

    load(id) {
        this.requestStatusLogService.find(id)
            .subscribe((requestStatusLogResponse: HttpResponse<RequestStatusLog>) => {
                this.requestStatusLog = requestStatusLogResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestStatusLogs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestStatusLogListModification',
            (response) => this.load(this.requestStatusLog.id)
        );
    }
}
