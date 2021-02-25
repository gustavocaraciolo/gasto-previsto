import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFinanceiro, Financeiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from './financeiro.service';
import { FinanceiroComponent } from './financeiro.component';
import { FinanceiroDetailComponent } from './financeiro-detail.component';
import { FinanceiroUpdateComponent } from './financeiro-update.component';

@Injectable({ providedIn: 'root' })
export class FinanceiroResolve implements Resolve<IFinanceiro> {
  constructor(private service: FinanceiroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFinanceiro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((financeiro: HttpResponse<Financeiro>) => {
          if (financeiro.body) {
            return of(financeiro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Financeiro());
  }
}

export const financeiroRoute: Routes = [
  {
    path: '',
    component: FinanceiroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.financeiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FinanceiroDetailComponent,
    resolve: {
      financeiro: FinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.financeiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FinanceiroUpdateComponent,
    resolve: {
      financeiro: FinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.financeiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FinanceiroUpdateComponent,
    resolve: {
      financeiro: FinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.financeiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
