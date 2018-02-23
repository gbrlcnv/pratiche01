import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestStatusLogComponent } from './request-status-log.component';
import { RequestStatusLogDetailComponent } from './request-status-log-detail.component';
import { RequestStatusLogPopupComponent } from './request-status-log-dialog.component';
import { RequestStatusLogDeletePopupComponent } from './request-status-log-delete-dialog.component';

export const requestStatusLogRoute: Routes = [
    {
        path: 'request-status-log',
        component: RequestStatusLogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatusLogs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-status-log/:id',
        component: RequestStatusLogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatusLogs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestStatusLogPopupRoute: Routes = [
    {
        path: 'request-status-log-new',
        component: RequestStatusLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatusLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-status-log/:id/edit',
        component: RequestStatusLogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatusLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-status-log/:id/delete',
        component: RequestStatusLogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestStatusLogs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
