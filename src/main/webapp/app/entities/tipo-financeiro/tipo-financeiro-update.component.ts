import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoFinanceiro, TipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { TipoFinanceiroService } from './tipo-financeiro.service';
import { IFinanceiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';

@Component({
  selector: 'jhi-tipo-financeiro-update',
  templateUrl: './tipo-financeiro-update.component.html',
})
export class TipoFinanceiroUpdateComponent implements OnInit {
  isSaving = false;
  financeiros: IFinanceiro[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [],
    tipoFinanceiro: [],
  });

  constructor(
    protected tipoFinanceiroService: TipoFinanceiroService,
    protected financeiroService: FinanceiroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoFinanceiro }) => {
      this.updateForm(tipoFinanceiro);

      this.financeiroService.query().subscribe((res: HttpResponse<IFinanceiro[]>) => (this.financeiros = res.body || []));
    });
  }

  updateForm(tipoFinanceiro: ITipoFinanceiro): void {
    this.editForm.patchValue({
      id: tipoFinanceiro.id,
      nome: tipoFinanceiro.nome,
      tipoFinanceiro: tipoFinanceiro.tipoFinanceiro,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoFinanceiro = this.createFromForm();
    if (tipoFinanceiro.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoFinanceiroService.update(tipoFinanceiro));
    } else {
      this.subscribeToSaveResponse(this.tipoFinanceiroService.create(tipoFinanceiro));
    }
  }

  private createFromForm(): ITipoFinanceiro {
    return {
      ...new TipoFinanceiro(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      tipoFinanceiro: this.editForm.get(['tipoFinanceiro'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoFinanceiro>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IFinanceiro): any {
    return item.id;
  }
}
