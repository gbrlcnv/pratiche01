import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RequestStatusLog } from './request-status-log.model';
import { RequestStatusLogService } from './request-status-log.service';

@Injectable()
export class RequestStatusLogPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private requestStatusLogService: RequestStatusLogService

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
                this.requestStatusLogService.find(id)
                    .subscribe((requestStatusLogResponse: HttpResponse<RequestStatusLog>) => {
                        const requestStatusLog: RequestStatusLog = requestStatusLogResponse.body;
                        requestStatusLog.statusFromDate = this.datePipe
                            .transform(requestStatusLog.statusFromDate, 'yyyy-MM-ddTHH:mm:ss');
                        requestStatusLog.statusChangeDate = this.datePipe
                            .transform(requestStatusLog.statusChangeDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.requestStatusLogModalRef(component, requestStatusLog);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.requestStatusLogModalRef(component, new RequestStatusLog());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    requestStatusLogModalRef(component: Component, requestStatusLog: RequestStatusLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestStatusLog = requestStatusLog;
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
