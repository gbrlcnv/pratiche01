import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';

@Component({
    selector: 'jhi-request-ricorso-tributario-detail',
    templateUrl: './request-ricorso-tributario-detail.component.html'
})
export class RequestRicorsoTributarioDetailComponent implements OnInit, OnDestroy {

    requestRicorsoTributario: RequestRicorsoTributario;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private requestRicorsoTributarioService: RequestRicorsoTributarioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRequestRicorsoTributarios();
    }

    load(id) {
        this.requestRicorsoTributarioService.find(id)
            .subscribe((requestRicorsoTributarioResponse: HttpResponse<RequestRicorsoTributario>) => {
                this.requestRicorsoTributario = requestRicorsoTributarioResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRequestRicorsoTributarios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'requestRicorsoTributarioListModification',
            (response) => this.load(this.requestRicorsoTributario.id)
        );
    }
}
