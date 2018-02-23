import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestDoc } from './request-doc.model';
import { RequestDocPopupService } from './request-doc-popup.service';
import { RequestDocService } from './request-doc.service';

@Component({
    selector: 'jhi-request-doc-delete-dialog',
    templateUrl: './request-doc-delete-dialog.component.html'
})
export class RequestDocDeleteDialogComponent {

    requestDoc: RequestDoc;

    constructor(
        private requestDocService: RequestDocService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestDocService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestDocListModification',
                content: 'Deleted an requestDoc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-doc-delete-popup',
    template: ''
})
export class RequestDocDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestDocPopupService: RequestDocPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestDocPopupService
                .open(RequestDocDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
