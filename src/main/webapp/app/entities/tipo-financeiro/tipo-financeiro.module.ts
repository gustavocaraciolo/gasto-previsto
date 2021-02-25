import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GastoPrevistoSharedModule } from 'app/shared/shared.module';
import { TipoFinanceiroComponent } from './tipo-financeiro.component';
import { TipoFinanceiroDetailComponent } from './tipo-financeiro-detail.component';
import { TipoFinanceiroUpdateComponent } from './tipo-financeiro-update.component';
import { TipoFinanceiroDeleteDialogComponent } from './tipo-financeiro-delete-dialog.component';
import { tipoFinanceiroRoute } from './tipo-financeiro.route';

@NgModule({
  imports: [GastoPrevistoSharedModule, RouterModule.forChild(tipoFinanceiroRoute)],
  declarations: [
    TipoFinanceiroComponent,
    TipoFinanceiroDetailComponent,
    TipoFinanceiroUpdateComponent,
    TipoFinanceiroDeleteDialogComponent,
  ],
  entryComponents: [TipoFinanceiroDeleteDialogComponent],
})
export class GastoPrevistoTipoFinanceiroModule {}
