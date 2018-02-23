import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Pratiche01SharedModule } from '../../shared';
import {
    RequestStatusLogService,
    RequestStatusLogPopupService,
    RequestStatusLogComponent,
    RequestStatusLogDetailComponent,
    RequestStatusLogDialogComponent,
    RequestStatusLogPopupComponent,
    RequestStatusLogDeletePopupComponent,
    RequestStatusLogDeleteDialogComponent,
    requestStatusLogRoute,
    requestStatusLogPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestStatusLogRoute,
    ...requestStatusLogPopupRoute,
];

@NgModule({
    imports: [
        Pratiche01SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestStatusLogComponent,
        RequestStatusLogDetailComponent,
        RequestStatusLogDialogComponent,
        RequestStatusLogDeleteDialogComponent,
        RequestStatusLogPopupComponent,
        RequestStatusLogDeletePopupComponent,
    ],
    entryComponents: [
        RequestStatusLogComponent,
        RequestStatusLogDialogComponent,
        RequestStatusLogPopupComponent,
        RequestStatusLogDeleteDialogComponent,
        RequestStatusLogDeletePopupComponent,
    ],
    providers: [
        RequestStatusLogService,
        RequestStatusLogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Pratiche01RequestStatusLogModule {}
