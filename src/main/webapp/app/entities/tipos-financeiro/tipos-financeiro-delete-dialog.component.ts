import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';
import { TiposFinanceiroService } from './tipos-financeiro.service';

@Component({
  templateUrl: './tipos-financeiro-delete-dialog.component.html',
})
export class TiposFinanceiroDeleteDialogComponent {
  tiposFinanceiro?: ITiposFinanceiro;

  constructor(
    protected tiposFinanceiroService: TiposFinanceiroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tiposFinanceiroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tiposFinanceiroListModification');
      this.activeModal.close();
    });
  }
}
