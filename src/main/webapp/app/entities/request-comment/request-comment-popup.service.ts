import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RequestComment } from './request-comment.model';
import { RequestCommentService } from './request-comment.service';

@Injectable()
export class RequestCommentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private requestCommentService: RequestCommentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.requestCommentService.find(id)
                    .subscribe((requestCommentResponse: HttpResponse<RequestComment>) => {
                        const requestComment: RequestComment = requestCommentResponse.body;
                        requestComment.commentDate = this.datePipe
                            .transform(requestComment.commentDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.requestCommentModalRef(component, requestComment);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.requestCommentModalRef(component, new RequestComment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    requestCommentModalRef(component: Component, requestComment: RequestComment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestComment = requestComment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
