import { IPropriedades } from 'app/shared/model/propriedades.model';
import { IFinanceiro } from 'app/shared/model/financeiro.model';

export interface IAnexo {
  id?: number;
  nomeContentType?: string;
  nome?: any;
  proriedades?: IPropriedades[];
  financeiros?: IFinanceiro[];
}

export class Anexo implements IAnexo {
  constructor(
    public id?: number,
    public nomeContentType?: string,
    public nome?: any,
    public proriedades?: IPropriedades[],
    public financeiros?: IFinanceiro[]
  ) {}
}
