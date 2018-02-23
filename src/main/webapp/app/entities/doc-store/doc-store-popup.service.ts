import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { DocStore } from './doc-store.model';
import { DocStoreService } from './doc-store.service';

@Injectable()
export class DocStorePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private docStoreService: DocStoreService

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
                this.docStoreService.find(id)
                    .subscribe((docStoreResponse: HttpResponse<DocStore>) => {
                        const docStore: DocStore = docStoreResponse.body;
                        docStore.creationDate = this.datePipe
                            .transform(docStore.creationDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.docStoreModalRef(component, docStore);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.docStoreModalRef(component, new DocStore());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    docStoreModalRef(component: Component, docStore: DocStore): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.docStore = docStore;
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
