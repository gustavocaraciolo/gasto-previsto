import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFinanceiro } from 'app/shared/model/financeiro.model';

type EntityResponseType = HttpResponse<IFinanceiro>;
type EntityArrayResponseType = HttpResponse<IFinanceiro[]>;

@Injectable({ providedIn: 'root' })
export class FinanceiroService {
  public resourceUrl = SERVER_API_URL + 'api/financeiros';

  constructor(protected http: HttpClient) {}

  create(financeiro: IFinanceiro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(financeiro);
    return this.http
      .post<IFinanceiro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(financeiro: IFinanceiro): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(financeiro);
    return this.http
      .put<IFinanceiro>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFinanceiro>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFinanceiro[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(financeiro: IFinanceiro): IFinanceiro {
    const copy: IFinanceiro = Object.assign({}, financeiro, {
      dataPagamento:
        financeiro.dataPagamento && financeiro.dataPagamento.isValid() ? financeiro.dataPagamento.format(DATE_FORMAT) : undefined,
      dataVencimento:
        financeiro.dataVencimento && financeiro.dataVencimento.isValid() ? financeiro.dataVencimento.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataPagamento = res.body.dataPagamento ? moment(res.body.dataPagamento) : undefined;
      res.body.dataVencimento = res.body.dataVencimento ? moment(res.body.dataVencimento) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((financeiro: IFinanceiro) => {
        financeiro.dataPagamento = financeiro.dataPagamento ? moment(financeiro.dataPagamento) : undefined;
        financeiro.dataVencimento = financeiro.dataVencimento ? moment(financeiro.dataVencimento) : undefined;
      });
    }
    return res;
  }
}
