import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';

@Component({
  selector: 'jhi-tipos-financeiro-detail',
  templateUrl: './tipos-financeiro-detail.component.html',
})
export class TiposFinanceiroDetailComponent implements OnInit {
  tiposFinanceiro: ITiposFinanceiro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiposFinanceiro }) => (this.tiposFinanceiro = tiposFinanceiro));
  }

  previousState(): void {
    window.history.back();
  }
}
