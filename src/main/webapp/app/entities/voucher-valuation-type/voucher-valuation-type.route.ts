import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { VoucherValuationTypeComponent } from './voucher-valuation-type.component';
import { VoucherValuationTypeDetailComponent } from './voucher-valuation-type-detail.component';
import { VoucherValuationTypePopupComponent } from './voucher-valuation-type-dialog.component';
import { VoucherValuationTypeDeletePopupComponent } from './voucher-valuation-type-delete-dialog.component';

export const voucherValuationTypeRoute: Routes = [
    {
        path: 'voucher-valuation-type',
        component: VoucherValuationTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'voucher-valuation-type/:id',
        component: VoucherValuationTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuationType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherValuationTypePopupRoute: Routes = [
    {
        path: 'voucher-valuation-type-new',
        component: VoucherValuationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-valuation-type/:id/edit',
        component: VoucherValuationTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-valuation-type/:id/delete',
        component: VoucherValuationTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuationType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
