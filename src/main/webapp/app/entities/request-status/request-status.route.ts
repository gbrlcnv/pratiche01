import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestStatusComponent } from './request-status.component';
import { RequestStatusDetailComponent } from './request-status-detail.component';
import { RequestStatusPopupComponent } from './request-status-dialog.component';
import { RequestStatusDeletePopupComponent } from './request-status-delete-dialog.component';

export const requestStatusRoute: Routes = [
    {
        path: 'request-status',
        component: RequestStatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-status/:id',
        component: RequestStatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestStatusPopupRoute: Routes = [
    {
        path: 'request-status-new',
        component: RequestStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-status/:id/edit',
        component: RequestStatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-status/:id/delete',
        component: RequestStatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
