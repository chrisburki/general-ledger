import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LedgerAccountComponent } from './ledger-account.component';
import { LedgerAccountDetailComponent } from './ledger-account-detail.component';
import { LedgerAccountPopupComponent } from './ledger-account-dialog.component';
import { LedgerAccountDeletePopupComponent } from './ledger-account-delete-dialog.component';

export const ledgerAccountRoute: Routes = [
    {
        path: 'ledger-account',
        component: LedgerAccountComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.ledgerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ledger-account/:id',
        component: LedgerAccountDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.ledgerAccount.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ledgerAccountPopupRoute: Routes = [
    {
        path: 'ledger-account-new',
        component: LedgerAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.ledgerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ledger-account/:id/edit',
        component: LedgerAccountPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.ledgerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ledger-account/:id/delete',
        component: LedgerAccountDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.ledgerAccount.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
