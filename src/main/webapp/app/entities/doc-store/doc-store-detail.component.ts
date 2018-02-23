import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DocStore } from './doc-store.model';
import { DocStoreService } from './doc-store.service';

@Component({
    selector: 'jhi-doc-store-detail',
    templateUrl: './doc-store-detail.component.html'
})
export class DocStoreDetailComponent implements OnInit, OnDestroy {

    docStore: DocStore;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private docStoreService: DocStoreService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDocStores();
    }

    load(id) {
        this.docStoreService.find(id)
            .subscribe((docStoreResponse: HttpResponse<DocStore>) => {
                this.docStore = docStoreResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDocStores() {
        this.eventSubscriber = this.eventManager.subscribe(
            'docStoreListModification',
            (response) => this.load(this.docStore.id)
        );
    }
}
