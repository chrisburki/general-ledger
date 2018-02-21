import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VoucherValuationComponent } from './voucher-valuation.component';
import { VoucherValuationDetailComponent } from './voucher-valuation-detail.component';
import { VoucherValuationPopupComponent } from './voucher-valuation-dialog.component';
import { VoucherValuationDeletePopupComponent } from './voucher-valuation-delete-dialog.component';

@Injectable()
export class VoucherValuationResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const voucherValuationRoute: Routes = [
    {
        path: 'voucher-valuation',
        component: VoucherValuationComponent,
        resolve: {
            'pagingParams': VoucherValuationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'voucher-valuation/:id',
        component: VoucherValuationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherValuationPopupRoute: Routes = [
    {
        path: 'voucher-valuation-new',
        component: VoucherValuationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-valuation/:id/edit',
        component: VoucherValuationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-valuation/:id/delete',
        component: VoucherValuationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherValuation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
