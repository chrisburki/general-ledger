import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VoucherPositionComponent } from './voucher-position.component';
import { VoucherPositionDetailComponent } from './voucher-position-detail.component';
import { VoucherPositionPopupComponent } from './voucher-position-dialog.component';
import { VoucherPositionDeletePopupComponent } from './voucher-position-delete-dialog.component';

@Injectable()
export class VoucherPositionResolvePagingParams implements Resolve<any> {

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

export const voucherPositionRoute: Routes = [
    {
        path: 'voucher-position',
        component: VoucherPositionComponent,
        resolve: {
            'pagingParams': VoucherPositionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherPosition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'voucher-position/:id',
        component: VoucherPositionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherPosition.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherPositionPopupRoute: Routes = [
    {
        path: 'voucher-position-new',
        component: VoucherPositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherPosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-position/:id/edit',
        component: VoucherPositionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherPosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-position/:id/delete',
        component: VoucherPositionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherPosition.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
