import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestDocComponent } from './request-doc.component';
import { RequestDocDetailComponent } from './request-doc-detail.component';
import { RequestDocPopupComponent } from './request-doc-dialog.component';
import { RequestDocDeletePopupComponent } from './request-doc-delete-dialog.component';

export const requestDocRoute: Routes = [
    {
        path: 'request-doc',
        component: RequestDocComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestDocs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-doc/:id',
        component: RequestDocDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestDocs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestDocPopupRoute: Routes = [
    {
        path: 'request-doc-new',
        component: RequestDocPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestDocs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-doc/:id/edit',
        component: RequestDocPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestDocs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-doc/:id/delete',
        component: RequestDocDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestDocs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
