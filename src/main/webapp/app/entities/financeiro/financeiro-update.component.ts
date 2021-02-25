import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFinanceiro, Financeiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from './financeiro.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { TipoFinanceiroService } from 'app/entities/tipo-financeiro/tipo-financeiro.service';
import { IAnexo } from 'app/shared/model/anexo.model';
import { AnexoService } from 'app/entities/anexo/anexo.service';

type SelectableEntity = IUser | ITipoFinanceiro | IAnexo;

@Component({
  selector: 'jhi-financeiro-update',
  templateUrl: './financeiro-update.component.html',
})
export class FinanceiroUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  tipofinanceiros: ITipoFinanceiro[] = [];
  anexos: IAnexo[] = [];
  dataPagamentoDp: any;
  dataVencimentoDp: any;

  editForm = this.fb.group({
    id: [],
    dataPagamento: [],
    dataVencimento: [],
    valorPlanejado: [],
    valorPago: [],
    periodicidade: [],
    quantidadeParcelas: [],
    moeda: [],
    descricao: [],
    categoriaFinanceiro: [],
    user: [],
    financeiro: [],
    anexos: [],
  });

  constructor(
    protected financeiroService: FinanceiroService,
    protected userService: UserService,
    protected tipoFinanceiroService: TipoFinanceiroService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ financeiro }) => {
      this.updateForm(financeiro);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.tipoFinanceiroService.query().subscribe((res: HttpResponse<ITipoFinanceiro[]>) => (this.tipofinanceiros = res.body || []));

      this.anexoService.query().subscribe((res: HttpResponse<IAnexo[]>) => (this.anexos = res.body || []));
    });
  }

  updateForm(financeiro: IFinanceiro): void {
    this.editForm.patchValue({
      id: financeiro.id,
      dataPagamento: financeiro.dataPagamento,
      dataVencimento: financeiro.dataVencimento,
      valorPlanejado: financeiro.valorPlanejado,
      valorPago: financeiro.valorPago,
      periodicidade: financeiro.periodicidade,
      quantidadeParcelas: financeiro.quantidadeParcelas,
      moeda: financeiro.moeda,
      descricao: financeiro.descricao,
      categoriaFinanceiro: financeiro.categoriaFinanceiro,
      user: financeiro.user,
      financeiro: financeiro.financeiro,
      anexos: financeiro.anexos,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const financeiro = this.createFromForm();
    if (financeiro.id !== undefined) {
      this.subscribeToSaveResponse(this.financeiroService.update(financeiro));
    } else {
      this.subscribeToSaveResponse(this.financeiroService.create(financeiro));
    }
  }

  private createFromForm(): IFinanceiro {
    return {
      ...new Financeiro(),
      id: this.editForm.get(['id'])!.value,
      dataPagamento: this.editForm.get(['dataPagamento'])!.value,
      dataVencimento: this.editForm.get(['dataVencimento'])!.value,
      valorPlanejado: this.editForm.get(['valorPlanejado'])!.value,
      valorPago: this.editForm.get(['valorPago'])!.value,
      periodicidade: this.editForm.get(['periodicidade'])!.value,
      quantidadeParcelas: this.editForm.get(['quantidadeParcelas'])!.value,
      moeda: this.editForm.get(['moeda'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      categoriaFinanceiro: this.editForm.get(['categoriaFinanceiro'])!.value,
      user: this.editForm.get(['user'])!.value,
      financeiro: this.editForm.get(['financeiro'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinanceiro>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IAnexo[], option: IAnexo): IAnexo {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
