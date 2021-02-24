import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPropriedades } from 'app/shared/model/propriedades.model';

type EntityResponseType = HttpResponse<IPropriedades>;
type EntityArrayResponseType = HttpResponse<IPropriedades[]>;

@Injectable({ providedIn: 'root' })
export class PropriedadesService {
  public resourceUrl = SERVER_API_URL + 'api/propriedades';

  constructor(protected http: HttpClient) {}

  create(propriedades: IPropriedades): Observable<EntityResponseType> {
    return this.http.post<IPropriedades>(this.resourceUrl, propriedades, { observe: 'response' });
  }

  update(propriedades: IPropriedades): Observable<EntityResponseType> {
    return this.http.put<IPropriedades>(this.resourceUrl, propriedades, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPropriedades>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPropriedades[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
