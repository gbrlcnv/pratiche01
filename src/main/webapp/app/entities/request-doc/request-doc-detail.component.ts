import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestDoc } from './request-doc.model';
import { RequestDocService } from './request-doc.service';

@Component({
    selector: 'jhi-request-doc-detail',
    templateUrl: './request-doc-detail.component.html'
})
export class RequestDocDetailComponent implements OnInit, OnDestroy {

    requestDoc: RequestDoc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestDocService: RequestDocService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestDocs();
    }

    load(id) {
        this.requestDocService.find(id)
            .subscribe((requestDocResponse: HttpResponse<RequestDoc>) => {
                this.requestDoc = requestDocResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestDocs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestDocListModification',
            (response) => this.load(this.requestDoc.id)
        );
    }
}
