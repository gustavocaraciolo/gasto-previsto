import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GastoPrevistoSharedModule } from 'app/shared/shared.module';
import { PropriedadesComponent } from './propriedades.component';
import { PropriedadesDetailComponent } from './propriedades-detail.component';
import { PropriedadesUpdateComponent } from './propriedades-update.component';
import { PropriedadesDeleteDialogComponent } from './propriedades-delete-dialog.component';
import { propriedadesRoute } from './propriedades.route';

@NgModule({
  imports: [GastoPrevistoSharedModule, RouterModule.forChild(propriedadesRoute)],
  declarations: [PropriedadesComponent, PropriedadesDetailComponent, PropriedadesUpdateComponent, PropriedadesDeleteDialogComponent],
  entryComponents: [PropriedadesDeleteDialogComponent],
})
export class GastoPrevistoPropriedadesModule {}
