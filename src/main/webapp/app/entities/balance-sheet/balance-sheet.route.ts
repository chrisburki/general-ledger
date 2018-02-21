import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BalanceSheetComponent } from './balance-sheet.component';
import { BalanceSheetDetailComponent } from './balance-sheet-detail.component';
import { BalanceSheetPopupComponent } from './balance-sheet-dialog.component';
import { BalanceSheetDeletePopupComponent } from './balance-sheet-delete-dialog.component';

export const balanceSheetRoute: Routes = [
    {
        path: 'balance-sheet',
        component: BalanceSheetComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'balance-sheet/:id',
        component: BalanceSheetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheet.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const balanceSheetPopupRoute: Routes = [
    {
        path: 'balance-sheet-new',
        component: BalanceSheetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-sheet/:id/edit',
        component: BalanceSheetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-sheet/:id/delete',
        component: BalanceSheetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheet.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
