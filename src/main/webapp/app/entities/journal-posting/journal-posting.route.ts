import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { JournalPostingComponent } from './journal-posting.component';
import { JournalPostingDetailComponent } from './journal-posting-detail.component';
import { JournalPostingPopupComponent } from './journal-posting-dialog.component';
import { JournalPostingDeletePopupComponent } from './journal-posting-delete-dialog.component';

@Injectable()
export class JournalPostingResolvePagingParams implements Resolve<any> {

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

export const journalPostingRoute: Routes = [
    {
        path: 'journal-posting',
        component: JournalPostingComponent,
        resolve: {
            'pagingParams': JournalPostingResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.journalPosting.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'journal-posting/:id',
        component: JournalPostingDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.journalPosting.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const journalPostingPopupRoute: Routes = [
    {
        path: 'journal-posting-new',
        component: JournalPostingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.journalPosting.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'journal-posting/:id/edit',
        component: JournalPostingPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.journalPosting.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'journal-posting/:id/delete',
        component: JournalPostingDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.journalPosting.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
