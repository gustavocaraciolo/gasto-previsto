import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFinanceiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from './financeiro.service';

@Component({
  templateUrl: './financeiro-delete-dialog.component.html',
})
export class FinanceiroDeleteDialogComponent {
  financeiro?: IFinanceiro;

  constructor(
    protected financeiroService: FinanceiroService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.financeiroService.delete(id).subscribe(() => {
      this.eventManager.broadcast('financeiroListModification');
      this.activeModal.close();
    });
  }
}
