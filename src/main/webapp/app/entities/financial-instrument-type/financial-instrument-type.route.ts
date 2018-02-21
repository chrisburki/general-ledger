import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FinancialInstrumentTypeComponent } from './financial-instrument-type.component';
import { FinancialInstrumentTypeDetailComponent } from './financial-instrument-type-detail.component';
import { FinancialInstrumentTypePopupComponent } from './financial-instrument-type-dialog.component';
import { FinancialInstrumentTypeDeletePopupComponent } from './financial-instrument-type-delete-dialog.component';

export const financialInstrumentTypeRoute: Routes = [
    {
        path: 'financial-instrument-type',
        component: FinancialInstrumentTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.financialInstrumentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'financial-instrument-type/:id',
        component: FinancialInstrumentTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.financialInstrumentType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const financialInstrumentTypePopupRoute: Routes = [
    {
        path: 'financial-instrument-type-new',
        component: FinancialInstrumentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.financialInstrumentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-instrument-type/:id/edit',
        component: FinancialInstrumentTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.financialInstrumentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'financial-instrument-type/:id/delete',
        component: FinancialInstrumentTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ledgerApp.financialInstrumentType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
