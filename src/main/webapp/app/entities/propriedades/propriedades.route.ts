import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPropriedades, Propriedades } from 'app/shared/model/propriedades.model';
import { PropriedadesService } from './propriedades.service';
import { PropriedadesComponent } from './propriedades.component';
import { PropriedadesDetailComponent } from './propriedades-detail.component';
import { PropriedadesUpdateComponent } from './propriedades-update.component';

@Injectable({ providedIn: 'root' })
export class PropriedadesResolve implements Resolve<IPropriedades> {
  constructor(private service: PropriedadesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPropriedades> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((propriedades: HttpResponse<Propriedades>) => {
          if (propriedades.body) {
            return of(propriedades.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Propriedades());
  }
}

export const propriedadesRoute: Routes = [
  {
    path: '',
    component: PropriedadesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.propriedades.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PropriedadesDetailComponent,
    resolve: {
      propriedades: PropriedadesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.propriedades.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PropriedadesUpdateComponent,
    resolve: {
      propriedades: PropriedadesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.propriedades.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PropriedadesUpdateComponent,
    resolve: {
      propriedades: PropriedadesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gastoPrevistoApp.propriedades.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
