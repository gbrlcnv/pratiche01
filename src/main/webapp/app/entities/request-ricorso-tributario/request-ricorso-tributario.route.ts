import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestRicorsoTributarioComponent } from './request-ricorso-tributario.component';
import { RequestRicorsoTributarioDetailComponent } from './request-ricorso-tributario-detail.component';
import { RequestRicorsoTributarioPopupComponent } from './request-ricorso-tributario-dialog.component';
import { RequestRicorsoTributarioDeletePopupComponent } from './request-ricorso-tributario-delete-dialog.component';

export const requestRicorsoTributarioRoute: Routes = [
    {
        path: 'request-ricorso-tributario',
        component: RequestRicorsoTributarioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestRicorsoTributarios'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-ricorso-tributario/:id',
        component: RequestRicorsoTributarioDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestRicorsoTributarios'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestRicorsoTributarioPopupRoute: Routes = [
    {
        path: 'request-ricorso-tributario-new',
        component: RequestRicorsoTributarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestRicorsoTributarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-ricorso-tributario/:id/edit',
        component: RequestRicorsoTributarioPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestRicorsoTributarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-ricorso-tributario/:id/delete',
        component: RequestRicorsoTributarioDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestRicorsoTributarios'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
