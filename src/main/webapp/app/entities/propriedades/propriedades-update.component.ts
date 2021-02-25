import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPropriedades, Propriedades } from 'app/shared/model/propriedades.model';
import { PropriedadesService } from './propriedades.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IFinanceiro } from 'app/shared/model/financeiro.model';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';
import { IAnexo } from 'app/shared/model/anexo.model';
import { AnexoService } from 'app/entities/anexo/anexo.service';

type SelectableEntity = IUser | IFinanceiro | IAnexo;

@Component({
  selector: 'jhi-propriedades-update',
  templateUrl: './propriedades-update.component.html',
})
export class PropriedadesUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  financeiros: IFinanceiro[] = [];
  anexos: IAnexo[] = [];
  dtAssContratoDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [],
    moeda: [],
    valorEstimado: [],
    dtAssContrato: [],
    descricao: [],
    tipoPagamento: [],
    user: [],
    financeiro: [],
    anexos: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected propriedadesService: PropriedadesService,
    protected userService: UserService,
    protected financeiroService: FinanceiroService,
    protected anexoService: AnexoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ propriedades }) => {
      this.updateForm(propriedades);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.financeiroService.query().subscribe((res: HttpResponse<IFinanceiro[]>) => (this.financeiros = res.body || []));

      this.anexoService.query().subscribe((res: HttpResponse<IAnexo[]>) => (this.anexos = res.body || []));
    });
  }

  updateForm(propriedades: IPropriedades): void {
    this.editForm.patchValue({
      id: propriedades.id,
      nome: propriedades.nome,
      moeda: propriedades.moeda,
      valorEstimado: propriedades.valorEstimado,
      dtAssContrato: propriedades.dtAssContrato,
      descricao: propriedades.descricao,
      tipoPagamento: propriedades.tipoPagamento,
      user: propriedades.user,
      financeiro: propriedades.financeiro,
      anexos: propriedades.anexos,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gastoPrevistoApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const propriedades = this.createFromForm();
    if (propriedades.id !== undefined) {
      this.subscribeToSaveResponse(this.propriedadesService.update(propriedades));
    } else {
      this.subscribeToSaveResponse(this.propriedadesService.create(propriedades));
    }
  }

  private createFromForm(): IPropriedades {
    return {
      ...new Propriedades(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      moeda: this.editForm.get(['moeda'])!.value,
      valorEstimado: this.editForm.get(['valorEstimado'])!.value,
      dtAssContrato: this.editForm.get(['dtAssContrato'])!.value,
      descricao: this.editForm.get(['descricao'])!.value,
      tipoPagamento: this.editForm.get(['tipoPagamento'])!.value,
      user: this.editForm.get(['user'])!.value,
      financeiro: this.editForm.get(['financeiro'])!.value,
      anexos: this.editForm.get(['anexos'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropriedades>>): void {
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
