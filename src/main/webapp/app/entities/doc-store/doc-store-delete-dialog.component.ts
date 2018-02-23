import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DocStore } from './doc-store.model';
import { DocStorePopupService } from './doc-store-popup.service';
import { DocStoreService } from './doc-store.service';

@Component({
    selector: 'jhi-doc-store-delete-dialog',
    templateUrl: './doc-store-delete-dialog.component.html'
})
export class DocStoreDeleteDialogComponent {

    docStore: DocStore;

    constructor(
        private docStoreService: DocStoreService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.docStoreService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'docStoreListModification',
                content: 'Deleted an docStore'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-doc-store-delete-popup',
    template: ''
})
export class DocStoreDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private docStorePopupService: DocStorePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.docStorePopupService
                .open(DocStoreDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
