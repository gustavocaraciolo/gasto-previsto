import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { TipoFinanceiroService } from './tipo-financeiro.service';
import { TipoFinanceiroDeleteDialogComponent } from './tipo-financeiro-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-financeiro',
  templateUrl: './tipo-financeiro.component.html',
})
export class TipoFinanceiroComponent implements OnInit, OnDestroy {
  tipoFinanceiros?: ITipoFinanceiro[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoFinanceiroService: TipoFinanceiroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoFinanceiroService.query().subscribe((res: HttpResponse<ITipoFinanceiro[]>) => (this.tipoFinanceiros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoFinanceiros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoFinanceiro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoFinanceiros(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoFinanceiroListModification', () => this.loadAll());
  }

  delete(tipoFinanceiro: ITipoFinanceiro): void {
    const modalRef = this.modalService.open(TipoFinanceiroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoFinanceiro = tipoFinanceiro;
  }
}
