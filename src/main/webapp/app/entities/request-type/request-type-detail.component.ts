import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestType } from './request-type.model';
import { RequestTypeService } from './request-type.service';

@Component({
    selector: 'jhi-request-type-detail',
    templateUrl: './request-type-detail.component.html'
})
export class RequestTypeDetailComponent implements OnInit, OnDestroy {

    requestType: RequestType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestTypeService: RequestTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestTypes();
    }

    load(id) {
        this.requestTypeService.find(id)
            .subscribe((requestTypeResponse: HttpResponse<RequestType>) => {
                this.requestType = requestTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestTypeListModification',
            (response) => this.load(this.requestType.id)
        );
    }
}
