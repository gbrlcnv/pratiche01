import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RequestType } from './request-type.model';
import { RequestTypeService } from './request-type.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-request-type',
    templateUrl: './request-type.component.html'
})
export class RequestTypeComponent implements OnInit, OnDestroy {
requestTypes: RequestType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private requestTypeService: RequestTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.requestTypeService.query().subscribe(
            (res: HttpResponse<RequestType[]>) => {
                this.requestTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInRequestTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: RequestType) {
        return item.id;
    }
    registerChangeInRequestTypes() {
        this.eventSubscriber = this.eventManager.subscribe('requestTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
