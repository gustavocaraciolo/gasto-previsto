import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFinanceiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from './financeiro.service';
import { FinanceiroDeleteDialogComponent } from './financeiro-delete-dialog.component';

@Component({
  selector: 'jhi-financeiro',
  templateUrl: './financeiro.component.html',
})
export class FinanceiroComponent implements OnInit, OnDestroy {
  financeiros?: IFinanceiro[];
  eventSubscriber?: Subscription;

  constructor(protected financeiroService: FinanceiroService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.financeiroService.query().subscribe((res: HttpResponse<IFinanceiro[]>) => (this.financeiros = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFinanceiros();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFinanceiro): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFinanceiros(): void {
    this.eventSubscriber = this.eventManager.subscribe('financeiroListModification', () => this.loadAll());
  }

  delete(financeiro: IFinanceiro): void {
    const modalRef = this.modalService.open(FinanceiroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.financeiro = financeiro;
  }
}
