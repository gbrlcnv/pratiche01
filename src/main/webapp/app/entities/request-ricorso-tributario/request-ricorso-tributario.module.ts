import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Pratiche01SharedModule } from '../../shared';
import {
    RequestRicorsoTributarioService,
    RequestRicorsoTributarioPopupService,
    RequestRicorsoTributarioComponent,
    RequestRicorsoTributarioDetailComponent,
    RequestRicorsoTributarioDialogComponent,
    RequestRicorsoTributarioPopupComponent,
    RequestRicorsoTributarioDeletePopupComponent,
    RequestRicorsoTributarioDeleteDialogComponent,
    requestRicorsoTributarioRoute,
    requestRicorsoTributarioPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestRicorsoTributarioRoute,
    ...requestRicorsoTributarioPopupRoute,
];

@NgModule({
    imports: [
        Pratiche01SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestRicorsoTributarioComponent,
        RequestRicorsoTributarioDetailComponent,
        RequestRicorsoTributarioDialogComponent,
        RequestRicorsoTributarioDeleteDialogComponent,
        RequestRicorsoTributarioPopupComponent,
        RequestRicorsoTributarioDeletePopupComponent,
    ],
    entryComponents: [
        RequestRicorsoTributarioComponent,
        RequestRicorsoTributarioDialogComponent,
        RequestRicorsoTributarioPopupComponent,
        RequestRicorsoTributarioDeleteDialogComponent,
        RequestRicorsoTributarioDeletePopupComponent,
    ],
    providers: [
        RequestRicorsoTributarioService,
        RequestRicorsoTributarioPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Pratiche01RequestRicorsoTributarioModule {}
