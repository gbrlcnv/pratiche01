import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { Pratiche01PersonModule } from './person/person.module';
import { Pratiche01RequestModule } from './request/request.module';
import { Pratiche01RequestStatusLogModule } from './request-status-log/request-status-log.module';
import { Pratiche01RequestTypeModule } from './request-type/request-type.module';
import { Pratiche01RequestStatusModule } from './request-status/request-status.module';
import { Pratiche01RequestDocModule } from './request-doc/request-doc.module';
import { Pratiche01RequestCommentModule } from './request-comment/request-comment.module';
import { Pratiche01DocStoreModule } from './doc-store/doc-store.module';
import { Pratiche01RequestRicorsoTributarioModule } from './request-ricorso-tributario/request-ricorso-tributario.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        Pratiche01PersonModule,
        Pratiche01RequestModule,
        Pratiche01RequestStatusLogModule,
        Pratiche01RequestTypeModule,
        Pratiche01RequestStatusModule,
        Pratiche01RequestDocModule,
        Pratiche01RequestCommentModule,
        Pratiche01DocStoreModule,
        Pratiche01RequestRicorsoTributarioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Pratiche01EntityModule {}
