import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Pratiche01SharedModule } from '../../shared';
import {
    RequestDocService,
    RequestDocPopupService,
    RequestDocComponent,
    RequestDocDetailComponent,
    RequestDocDialogComponent,
    RequestDocPopupComponent,
    RequestDocDeletePopupComponent,
    RequestDocDeleteDialogComponent,
    requestDocRoute,
    requestDocPopupRoute,
} from './';

const ENTITY_STATES = [
    ...requestDocRoute,
    ...requestDocPopupRoute,
];

@NgModule({
    imports: [
        Pratiche01SharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        RequestDocComponent,
        RequestDocDetailComponent,
        RequestDocDialogComponent,
        RequestDocDeleteDialogComponent,
        RequestDocPopupComponent,
        RequestDocDeletePopupComponent,
    ],
    entryComponents: [
        RequestDocComponent,
        RequestDocDialogComponent,
        RequestDocPopupComponent,
        RequestDocDeleteDialogComponent,
        RequestDocDeletePopupComponent,
    ],
    providers: [
        RequestDocService,
        RequestDocPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Pratiche01RequestDocModule {}
