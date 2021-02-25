import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITiposFinanceiro, TiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';
import { TiposFinanceiroService } from './tipos-financeiro.service';

@Component({
  selector: 'jhi-tipos-financeiro-update',
  templateUrl: './tipos-financeiro-update.component.html',
})
export class TiposFinanceiroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
  });

  constructor(
    protected tiposFinanceiroService: TiposFinanceiroService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tiposFinanceiro }) => {
      this.updateForm(tiposFinanceiro);
    });
  }

  updateForm(tiposFinanceiro: ITiposFinanceiro): void {
    this.editForm.patchValue({
      id: tiposFinanceiro.id,
      nome: tiposFinanceiro.nome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tiposFinanceiro = this.createFromForm();
    if (tiposFinanceiro.id !== undefined) {
      this.subscribeToSaveResponse(this.tiposFinanceiroService.update(tiposFinanceiro));
    } else {
      this.subscribeToSaveResponse(this.tiposFinanceiroService.create(tiposFinanceiro));
    }
  }

  private createFromForm(): ITiposFinanceiro {
    return {
      ...new TiposFinanceiro(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITiposFinanceiro>>): void {
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
}
