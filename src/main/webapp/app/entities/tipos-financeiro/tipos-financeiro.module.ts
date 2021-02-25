import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GastoPrevistoSharedModule } from 'app/shared/shared.module';
import { TiposFinanceiroComponent } from './tipos-financeiro.component';
import { TiposFinanceiroDetailComponent } from './tipos-financeiro-detail.component';
import { TiposFinanceiroUpdateComponent } from './tipos-financeiro-update.component';
import { TiposFinanceiroDeleteDialogComponent } from './tipos-financeiro-delete-dialog.component';
import { tiposFinanceiroRoute } from './tipos-financeiro.route';

@NgModule({
  imports: [GastoPrevistoSharedModule, RouterModule.forChild(tiposFinanceiroRoute)],
  declarations: [
    TiposFinanceiroComponent,
    TiposFinanceiroDetailComponent,
    TiposFinanceiroUpdateComponent,
    TiposFinanceiroDeleteDialogComponent,
  ],
  entryComponents: [TiposFinanceiroDeleteDialogComponent],
})
export class GastoPrevistoTiposFinanceiroModule {}
