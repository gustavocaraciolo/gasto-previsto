import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';

type EntityResponseType = HttpResponse<ITipoFinanceiro>;
type EntityArrayResponseType = HttpResponse<ITipoFinanceiro[]>;

@Injectable({ providedIn: 'root' })
export class TipoFinanceiroService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-financeiros';

  constructor(protected http: HttpClient) {}

  create(tipoFinanceiro: ITipoFinanceiro): Observable<EntityResponseType> {
    return this.http.post<ITipoFinanceiro>(this.resourceUrl, tipoFinanceiro, { observe: 'response' });
  }

  update(tipoFinanceiro: ITipoFinanceiro): Observable<EntityResponseType> {
    return this.http.put<ITipoFinanceiro>(this.resourceUrl, tipoFinanceiro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoFinanceiro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoFinanceiro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
