import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GastoPrevistoSharedModule } from 'app/shared/shared.module';
import { FinanceiroComponent } from './financeiro.component';
import { FinanceiroDetailComponent } from './financeiro-detail.component';
import { FinanceiroUpdateComponent } from './financeiro-update.component';
import { FinanceiroDeleteDialogComponent } from './financeiro-delete-dialog.component';
import { financeiroRoute } from './financeiro.route';

@NgModule({
  imports: [GastoPrevistoSharedModule, RouterModule.forChild(financeiroRoute)],
  declarations: [FinanceiroComponent, FinanceiroDetailComponent, FinanceiroUpdateComponent, FinanceiroDeleteDialogComponent],
  entryComponents: [FinanceiroDeleteDialogComponent],
})
export class GastoPrevistoFinanceiroModule {}
