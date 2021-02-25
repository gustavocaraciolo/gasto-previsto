import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';
import { TiposFinanceiroService } from './tipos-financeiro.service';
import { TiposFinanceiroDeleteDialogComponent } from './tipos-financeiro-delete-dialog.component';

@Component({
  selector: 'jhi-tipos-financeiro',
  templateUrl: './tipos-financeiro.component.html',
})
export class TiposFinanceiroComponent implements OnInit, OnDestroy {
  tiposFinanceiros?: ITiposFinanceiro[];
  eventSubscriber?: Subscription;

  constructor(
    protected tiposFinanceiroService: TiposFinanceiroService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tiposFinanceiroService.query().subscribe((res: HttpResponse<ITiposFinanceiro[]>) => (this.tiposFinanceiros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTiposFinanceiros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITiposFinanceiro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTiposFinanceiros(): void {
    this.eventSubscriber = this.eventManager.subscribe('tiposFinanceiroListModification', () => this.loadAll());
  }

  delete(tiposFinanceiro: ITiposFinanceiro): void {
    const modalRef = this.modalService.open(TiposFinanceiroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tiposFinanceiro = tiposFinanceiro;
  }
}
