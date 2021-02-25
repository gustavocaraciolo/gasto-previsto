import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITiposFinanceiro, TiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';
import { TiposFinanceiroService } from './tipos-financeiro.service';
import { TiposFinanceiroComponent } from './tipos-financeiro.component';
import { TiposFinanceiroDetailComponent } from './tipos-financeiro-detail.component';
import { TiposFinanceiroUpdateComponent } from './tipos-financeiro-update.component';

@Injectable({ providedIn: 'root' })
export class TiposFinanceiroResolve implements Resolve<ITiposFinanceiro> {
  constructor(private service: TiposFinanceiroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITiposFinanceiro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tiposFinanceiro: HttpResponse<TiposFinanceiro>) => {
          if (tiposFinanceiro.body) {
            return of(tiposFinanceiro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TiposFinanceiro());
  }
}

export const tiposFinanceiroRoute: Routes = [
  {
    path: '',
    component: TiposFinanceiroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tiposFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TiposFinanceiroDetailComponent,
    resolve: {
      tiposFinanceiro: TiposFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tiposFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TiposFinanceiroUpdateComponent,
    resolve: {
      tiposFinanceiro: TiposFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tiposFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TiposFinanceiroUpdateComponent,
    resolve: {
      tiposFinanceiro: TiposFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tiposFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
