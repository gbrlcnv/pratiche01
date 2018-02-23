import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestComment } from './request-comment.model';
import { RequestCommentPopupService } from './request-comment-popup.service';
import { RequestCommentService } from './request-comment.service';

@Component({
    selector: 'jhi-request-comment-delete-dialog',
    templateUrl: './request-comment-delete-dialog.component.html'
})
export class RequestCommentDeleteDialogComponent {

    requestComment: RequestComment;

    constructor(
        private requestCommentService: RequestCommentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestCommentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestCommentListModification',
                content: 'Deleted an requestComment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-comment-delete-popup',
    template: ''
})
export class RequestCommentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestCommentPopupService: RequestCommentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestCommentPopupService
                .open(RequestCommentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
