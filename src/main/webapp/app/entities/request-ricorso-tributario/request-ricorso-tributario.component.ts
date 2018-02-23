import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-ricorso-tributario',
    templateUrl: './request-ricorso-tributario.component.html'
})
export class RequestRicorsoTributarioComponent implements OnInit, OnDestroy {
requestRicorsoTributarios: RequestRicorsoTributario[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestRicorsoTributarioService: RequestRicorsoTributarioService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestRicorsoTributarioService.query().subscribe(
            (res: HttpResponse<RequestRicorsoTributario[]>) => {
                this.requestRicorsoTributarios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestRicorsoTributarios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestRicorsoTributario) {
        return item.id;
    }
    registerChangeInRequestRicorsoTributarios() {
        this.eventSubscriber = this.eventManager.subscribe('requestRicorsoTributarioListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
