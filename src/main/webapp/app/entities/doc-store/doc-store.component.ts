import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DocStore } from './doc-store.model';
import { DocStoreService } from './doc-store.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-doc-store',
    templateUrl: './doc-store.component.html'
})
export class DocStoreComponent implements OnInit, OnDestroy {
docStores: DocStore[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private docStoreService: DocStoreService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.docStoreService.query().subscribe(
            (res: HttpResponse<DocStore[]>) => {
                this.docStores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDocStores();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DocStore) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInDocStores() {
        this.eventSubscriber = this.eventManager.subscribe('docStoreListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
