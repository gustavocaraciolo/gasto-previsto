import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoFinanceiro, TipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { TipoFinanceiroService } from './tipo-financeiro.service';
import { TipoFinanceiroComponent } from './tipo-financeiro.component';
import { TipoFinanceiroDetailComponent } from './tipo-financeiro-detail.component';
import { TipoFinanceiroUpdateComponent } from './tipo-financeiro-update.component';

@Injectable({ providedIn: 'root' })
export class TipoFinanceiroResolve implements Resolve<ITipoFinanceiro> {
  constructor(private service: TipoFinanceiroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoFinanceiro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoFinanceiro: HttpResponse<TipoFinanceiro>) => {
          if (tipoFinanceiro.body) {
            return of(tipoFinanceiro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoFinanceiro());
  }
}

export const tipoFinanceiroRoute: Routes = [
  {
    path: '',
    component: TipoFinanceiroComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tipoFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoFinanceiroDetailComponent,
    resolve: {
      tipoFinanceiro: TipoFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tipoFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoFinanceiroUpdateComponent,
    resolve: {
      tipoFinanceiro: TipoFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tipoFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoFinanceiroUpdateComponent,
    resolve: {
      tipoFinanceiro: TipoFinanceiroResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.tipoFinanceiro.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
