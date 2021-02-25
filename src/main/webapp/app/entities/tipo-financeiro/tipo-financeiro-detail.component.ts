import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';

@Component({
  selector: 'jhi-tipo-financeiro-detail',
  templateUrl: './tipo-financeiro-detail.component.html',
})
export class TipoFinanceiroDetailComponent implements OnInit {
  tipoFinanceiro: ITipoFinanceiro | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFinanceiro }) => (this.tipoFinanceiro = tipoFinanceiro));
  }

  previousState(): void {
    window.history.back();
  }
}
