import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VoucherBookingComponent } from './voucher-booking.component';
import { VoucherBookingDetailComponent } from './voucher-booking-detail.component';
import { VoucherBookingPopupComponent } from './voucher-booking-dialog.component';
import { VoucherBookingDeletePopupComponent } from './voucher-booking-delete-dialog.component';

@Injectable()
export class VoucherBookingResolvePagingParams implements Resolve<any> {

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

export const voucherBookingRoute: Routes = [
    {
        path: 'voucher-booking',
        component: VoucherBookingComponent,
        resolve: {
            'pagingParams': VoucherBookingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherBooking.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'voucher-booking/:id',
        component: VoucherBookingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherBooking.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voucherBookingPopupRoute: Routes = [
    {
        path: 'voucher-booking-new',
        component: VoucherBookingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherBooking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-booking/:id/edit',
        component: VoucherBookingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherBooking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'voucher-booking/:id/delete',
        component: VoucherBookingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.voucherBooking.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
