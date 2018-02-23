import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestComment } from './request-comment.model';
import { RequestCommentService } from './request-comment.service';

@Component({
    selector: 'jhi-request-comment-detail',
    templateUrl: './request-comment-detail.component.html'
})
export class RequestCommentDetailComponent implements OnInit, OnDestroy {

    requestComment: RequestComment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestCommentService: RequestCommentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestComments();
    }

    load(id) {
        this.requestCommentService.find(id)
            .subscribe((requestCommentResponse: HttpResponse<RequestComment>) => {
                this.requestComment = requestCommentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestComments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestCommentListModification',
            (response) => this.load(this.requestComment.id)
        );
    }
}
