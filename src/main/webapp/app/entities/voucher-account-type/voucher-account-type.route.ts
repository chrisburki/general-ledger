import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VoucherAccountTypeComponent } from './voucher-account-type.component';
import { VoucherAccountTypeDetailComponent } from './voucher-account-type-detail.component';
import { VoucherAccountTypePopupComponent } from './voucher-account-type-dialog.component';
import { VoucherAccountTypeDeletePopupComponent } from './voucher-account-type-delete-dialog.component';

export const voucherAccountTypeRoute: Routes = [
    {
        path: 'voucher-account-type',
        component: VoucherAccountTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherAccountType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'voucher-account-type/:id',
        component: VoucherAccountTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherAccountType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherAccountTypePopupRoute: Routes = [
    {
        path: 'voucher-account-type-new',
        component: VoucherAccountTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherAccountType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-account-type/:id/edit',
        component: VoucherAccountTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherAccountType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-account-type/:id/delete',
        component: VoucherAccountTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherAccountType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
