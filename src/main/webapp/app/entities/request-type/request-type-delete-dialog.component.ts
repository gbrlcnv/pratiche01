import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestType } from './request-type.model';
import { RequestTypePopupService } from './request-type-popup.service';
import { RequestTypeService } from './request-type.service';

@Component({
    selector: 'jhi-request-type-delete-dialog',
    templateUrl: './request-type-delete-dialog.component.html'
})
export class RequestTypeDeleteDialogComponent {

    requestType: RequestType;

    constructor(
        private requestTypeService: RequestTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestTypeListModification',
                content: 'Deleted an requestType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-type-delete-popup',
    template: ''
})
export class RequestTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestTypePopupService: RequestTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestTypePopupService
                .open(RequestTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
