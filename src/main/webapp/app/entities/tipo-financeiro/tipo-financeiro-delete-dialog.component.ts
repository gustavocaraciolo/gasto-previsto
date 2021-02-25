import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { TipoFinanceiroService } from './tipo-financeiro.service';

@Component({
  templateUrl: './tipo-financeiro-delete-dialog.component.html',
})
export class TipoFinanceiroDeleteDialogComponent {
  tipoFinanceiro?: ITipoFinanceiro;

  constructor(
    protected tipoFinanceiroService: TipoFinanceiroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoFinanceiroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoFinanceiroListModification');
      this.activeModal.close();
    });
  }
}
