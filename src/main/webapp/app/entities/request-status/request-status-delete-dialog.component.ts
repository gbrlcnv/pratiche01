import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestStatus } from './request-status.model';
import { RequestStatusPopupService } from './request-status-popup.service';
import { RequestStatusService } from './request-status.service';

@Component({
    selector: 'jhi-request-status-delete-dialog',
    templateUrl: './request-status-delete-dialog.component.html'
})
export class RequestStatusDeleteDialogComponent {

    requestStatus: RequestStatus;

    constructor(
        private requestStatusService: RequestStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestStatusListModification',
                content: 'Deleted an requestStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-status-delete-popup',
    template: ''
})
export class RequestStatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestStatusPopupService: RequestStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestStatusPopupService
                .open(RequestStatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
