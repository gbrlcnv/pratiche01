import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RequestRicorsoTributario } from './request-ricorso-tributario.model';
import { RequestRicorsoTributarioPopupService } from './request-ricorso-tributario-popup.service';
import { RequestRicorsoTributarioService } from './request-ricorso-tributario.service';

@Component({
    selector: 'jhi-request-ricorso-tributario-delete-dialog',
    templateUrl: './request-ricorso-tributario-delete-dialog.component.html'
})
export class RequestRicorsoTributarioDeleteDialogComponent {

    requestRicorsoTributario: RequestRicorsoTributario;

    constructor(
        private requestRicorsoTributarioService: RequestRicorsoTributarioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.requestRicorsoTributarioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'requestRicorsoTributarioListModification',
                content: 'Deleted an requestRicorsoTributario'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-request-ricorso-tributario-delete-popup',
    template: ''
})
export class RequestRicorsoTributarioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private requestRicorsoTributarioPopupService: RequestRicorsoTributarioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.requestRicorsoTributarioPopupService
                .open(RequestRicorsoTributarioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
