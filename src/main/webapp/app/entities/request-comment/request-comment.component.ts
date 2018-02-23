import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestComment } from './request-comment.model';
import { RequestCommentService } from './request-comment.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-comment',
    templateUrl: './request-comment.component.html'
})
export class RequestCommentComponent implements OnInit, OnDestroy {
requestComments: RequestComment[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestCommentService: RequestCommentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestCommentService.query().subscribe(
            (res: HttpResponse<RequestComment[]>) => {
                this.requestComments = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestComments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestComment) {
        return item.id;
    }
    registerChangeInRequestComments() {
        this.eventSubscriber = this.eventManager.subscribe('requestCommentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
