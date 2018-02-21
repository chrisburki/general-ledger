import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SubLedgerTypeComponent } from './sub-ledger-type.component';
import { SubLedgerTypeDetailComponent } from './sub-ledger-type-detail.component';
import { SubLedgerTypePopupComponent } from './sub-ledger-type-dialog.component';
import { SubLedgerTypeDeletePopupComponent } from './sub-ledger-type-delete-dialog.component';

export const subLedgerTypeRoute: Routes = [
    {
        path: 'sub-ledger-type',
        component: SubLedgerTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.subLedgerType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sub-ledger-type/:id',
        component: SubLedgerTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.subLedgerType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subLedgerTypePopupRoute: Routes = [
    {
        path: 'sub-ledger-type-new',
        component: SubLedgerTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.subLedgerType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sub-ledger-type/:id/edit',
        component: SubLedgerTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.subLedgerType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sub-ledger-type/:id/delete',
        component: SubLedgerTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.subLedgerType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
