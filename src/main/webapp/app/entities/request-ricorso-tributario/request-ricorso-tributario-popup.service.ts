import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';

@Injectable()
export class RequestRicorsoTributarioPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private requestRicorsoTributarioService: RequestRicorsoTributarioService

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
                this.requestRicorsoTributarioService.find(id)
                    .subscribe((requestRicorsoTributarioResponse: HttpResponse<RequestRicorsoTributario>) => {
                        const requestRicorsoTributario: RequestRicorsoTributario = requestRicorsoTributarioResponse.body;
                        requestRicorsoTributario.notificaDate = this.datePipe
                            .transform(requestRicorsoTributario.notificaDate, 'yyyy-MM-ddTHH:mm:ss');
                        requestRicorsoTributario.emissioneRuoloDate = this.datePipe
                            .transform(requestRicorsoTributario.emissioneRuoloDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.requestRicorsoTributarioModalRef(component, requestRicorsoTributario);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.requestRicorsoTributarioModalRef(component, new RequestRicorsoTributario());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    requestRicorsoTributarioModalRef(component: Component, requestRicorsoTributario: RequestRicorsoTributario): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.requestRicorsoTributario = requestRicorsoTributario;
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
