import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedades } from 'app/shared/model/propriedades.model';
import { PropriedadesService } from './propriedades.service';

@Component({
  templateUrl: './propriedades-delete-dialog.component.html',
})
export class PropriedadesDeleteDialogComponent {
  propriedades?: IPropriedades;

  constructor(
    protected propriedadesService: PropriedadesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.propriedadesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('propriedadesListModification');
      this.activeModal.close();
    });
  }
}
