import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RequestDoc } from './request-doc.model';
import { RequestDocService } from './request-doc.service';

@Injectable()
export class RequestDocPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private requestDocService: RequestDocService

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
                this.requestDocService.find(id)
                    .subscribe((requestDocResponse: HttpResponse<RequestDoc>) => {
                        const requestDoc: RequestDoc = requestDocResponse.body;
                        requestDoc.submissionDate = this.datePipe
                            .transform(requestDoc.submissionDate, 'yyyy-MM-ddTHH:mm:ss');
                        requestDoc.updateDate = this.datePipe
                            .transform(requestDoc.updateDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.requestDocModalRef(component, requestDoc);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.requestDocModalRef(component, new RequestDoc());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    requestDocModalRef(component: Component, requestDoc: RequestDoc): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestDoc = requestDoc;
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
