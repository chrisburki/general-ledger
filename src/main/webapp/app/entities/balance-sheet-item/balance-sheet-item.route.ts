import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BalanceSheetItemComponent } from './balance-sheet-item.component';
import { BalanceSheetItemDetailComponent } from './balance-sheet-item-detail.component';
import { BalanceSheetItemPopupComponent } from './balance-sheet-item-dialog.component';
import { BalanceSheetItemDeletePopupComponent } from './balance-sheet-item-delete-dialog.component';

@Injectable()
export class BalanceSheetItemResolvePagingParams implements Resolve<any> {

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

export const balanceSheetItemRoute: Routes = [
    {
        path: 'balance-sheet-item',
        component: BalanceSheetItemComponent,
        resolve: {
            'pagingParams': BalanceSheetItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheetItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'balance-sheet-item/:id',
        component: BalanceSheetItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheetItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const balanceSheetItemPopupRoute: Routes = [
    {
        path: 'balance-sheet-item-new',
        component: BalanceSheetItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheetItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-sheet-item/:id/edit',
        component: BalanceSheetItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheetItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'balance-sheet-item/:id/delete',
        component: BalanceSheetItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.balanceSheetItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
