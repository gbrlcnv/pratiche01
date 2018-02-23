import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Pratiche01SharedModule } from '../../shared';
import {
    RequestStatusService,
    RequestStatusPopupService,
    RequestStatusComponent,
    RequestStatusDetailComponent,
    RequestStatusDialogComponent,
    RequestStatusPopupComponent,
    RequestStatusDeletePopupComponent,
    RequestStatusDeleteDialogComponent,
    requestStatusRoute,
    requestStatusPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestStatusRoute,
    ...requestStatusPopupRoute,
];

@NgModule({
    imports: [
        Pratiche01SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestStatusComponent,
        RequestStatusDetailComponent,
        RequestStatusDialogComponent,
        RequestStatusDeleteDialogComponent,
        RequestStatusPopupComponent,
        RequestStatusDeletePopupComponent,
    ],
    entryComponents: [
        RequestStatusComponent,
        RequestStatusDialogComponent,
        RequestStatusPopupComponent,
        RequestStatusDeleteDialogComponent,
        RequestStatusDeletePopupComponent,
    ],
    providers: [
        RequestStatusService,
        RequestStatusPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Pratiche01RequestStatusModule {}
