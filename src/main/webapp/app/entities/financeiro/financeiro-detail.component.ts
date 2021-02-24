import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFinanceiro } from 'app/shared/model/financeiro.model';

@Component({
  selector: 'jhi-financeiro-detail',
  templateUrl: './financeiro-detail.component.html',
})
export class FinanceiroDetailComponent implements OnInit {
  financeiro: IFinanceiro | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ financeiro }) => (this.financeiro = financeiro));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
