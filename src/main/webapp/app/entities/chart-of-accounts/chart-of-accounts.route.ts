import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ChartOfAccountsComponent } from './chart-of-accounts.component';
import { ChartOfAccountsDetailComponent } from './chart-of-accounts-detail.component';
import { ChartOfAccountsPopupComponent } from './chart-of-accounts-dialog.component';
import { ChartOfAccountsDeletePopupComponent } from './chart-of-accounts-delete-dialog.component';

export const chartOfAccountsRoute: Routes = [
    {
        path: 'chart-of-accounts',
        component: ChartOfAccountsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.chartOfAccounts.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chart-of-accounts/:id',
        component: ChartOfAccountsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.chartOfAccounts.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chartOfAccountsPopupRoute: Routes = [
    {
        path: 'chart-of-accounts-new',
        component: ChartOfAccountsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.chartOfAccounts.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chart-of-accounts/:id/edit',
        component: ChartOfAccountsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.chartOfAccounts.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chart-of-accounts/:id/delete',
        component: ChartOfAccountsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.chartOfAccounts.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
