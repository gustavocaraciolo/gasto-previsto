import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';

type EntityResponseType = HttpResponse<ITiposFinanceiro>;
type EntityArrayResponseType = HttpResponse<ITiposFinanceiro[]>;

@Injectable({ providedIn: 'root' })
export class TiposFinanceiroService {
  public resourceUrl = SERVER_API_URL + 'api/tipos-financeiros';

  constructor(protected http: HttpClient) {}

  create(tiposFinanceiro: ITiposFinanceiro): Observable<EntityResponseType> {
    return this.http.post<ITiposFinanceiro>(this.resourceUrl, tiposFinanceiro, { observe: 'response' });
  }

  update(tiposFinanceiro: ITiposFinanceiro): Observable<EntityResponseType> {
    return this.http.put<ITiposFinanceiro>(this.resourceUrl, tiposFinanceiro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITiposFinanceiro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITiposFinanceiro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
