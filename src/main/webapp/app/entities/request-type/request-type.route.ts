import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestTypeComponent } from './request-type.component';
import { RequestTypeDetailComponent } from './request-type-detail.component';
import { RequestTypePopupComponent } from './request-type-dialog.component';
import { RequestTypeDeletePopupComponent } from './request-type-delete-dialog.component';

export const requestTypeRoute: Routes = [
    {
        path: 'request-type',
        component: RequestTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-type/:id',
        component: RequestTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestTypePopupRoute: Routes = [
    {
        path: 'request-type-new',
        component: RequestTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-type/:id/edit',
        component: RequestTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-type/:id/delete',
        component: RequestTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
