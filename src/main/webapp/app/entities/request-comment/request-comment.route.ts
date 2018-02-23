import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { RequestCommentComponent } from './request-comment.component';
import { RequestCommentDetailComponent } from './request-comment-detail.component';
import { RequestCommentPopupComponent } from './request-comment-dialog.component';
import { RequestCommentDeletePopupComponent } from './request-comment-delete-dialog.component';

export const requestCommentRoute: Routes = [
    {
        path: 'request-comment',
        component: RequestCommentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestComments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'request-comment/:id',
        component: RequestCommentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestComments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const requestCommentPopupRoute: Routes = [
    {
        path: 'request-comment-new',
        component: RequestCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-comment/:id/edit',
        component: RequestCommentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'request-comment/:id/delete',
        component: RequestCommentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RequestComments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
