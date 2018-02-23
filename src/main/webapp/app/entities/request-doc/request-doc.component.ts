import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestDoc } from './request-doc.model';
import { RequestDocService } from './request-doc.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-doc',
    templateUrl: './request-doc.component.html'
})
export class RequestDocComponent implements OnInit, OnDestroy {
requestDocs: RequestDoc[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestDocService: RequestDocService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestDocService.query().subscribe(
            (res: HttpResponse<RequestDoc[]>) => {
                this.requestDocs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestDocs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestDoc) {
        return item.id;
    }
    registerChangeInRequestDocs() {
        this.eventSubscriber = this.eventManager.subscribe('requestDocListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
