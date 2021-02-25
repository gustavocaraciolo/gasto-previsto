import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFinanceiro } from 'app/shared/model/financeiro.model';

@Component({
  selector: 'jhi-financeiro-detail',
  templateUrl: './financeiro-detail.component.html',
})
export class FinanceiroDetailComponent implements OnInit {
  financeiro: IFinanceiro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ financeiro }) => (this.financeiro = financeiro));
  }

  previousState(): void {
    window.history.back();
  }
}
