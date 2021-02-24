import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'propriedades',
        loadChildren: () => import('./propriedades/propriedades.module').then(m => m.GastoPrevistoPropriedadesModule),
      },
      {
        path: 'financeiro',
        loadChildren: () => import('./financeiro/financeiro.module').then(m => m.GastoPrevistoFinanceiroModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GastoPrevistoEntityModule {}
