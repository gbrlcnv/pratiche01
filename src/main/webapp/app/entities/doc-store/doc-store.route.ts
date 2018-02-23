import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DocStoreComponent } from './doc-store.component';
import { DocStoreDetailComponent } from './doc-store-detail.component';
import { DocStorePopupComponent } from './doc-store-dialog.component';
import { DocStoreDeletePopupComponent } from './doc-store-delete-dialog.component';

export const docStoreRoute: Routes = [
    {
        path: 'doc-store',
        component: DocStoreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocStores'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'doc-store/:id',
        component: DocStoreDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocStores'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const docStorePopupRoute: Routes = [
    {
        path: 'doc-store-new',
        component: DocStorePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocStores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doc-store/:id/edit',
        component: DocStorePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocStores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'doc-store/:id/delete',
        component: DocStoreDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocStores'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
