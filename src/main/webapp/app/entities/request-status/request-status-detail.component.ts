import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestStatus } from './request-status.model';
import { RequestStatusService } from './request-status.service';

@Component({
    selector: 'jhi-request-status-detail',
    templateUrl: './request-status-detail.component.html'
})
export class RequestStatusDetailComponent implements OnInit, OnDestroy {

    requestStatus: RequestStatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestStatusService: RequestStatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestStatuses();
    }

    load(id) {
        this.requestStatusService.find(id)
            .subscribe((requestStatusResponse: HttpResponse<RequestStatus>) => {
                this.requestStatus = requestStatusResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestStatusListModification',
            (response) => this.load(this.requestStatus.id)
        );
    }
}
