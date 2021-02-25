import { IFinanceiro } from 'app/shared/model/financeiro.model';

export interface ITipoFinanceiro {
  id?: number;
  nome?: string;
  tipoFinanceiros?: IFinanceiro[];
}

export class TipoFinanceiro implements ITipoFinanceiro {
  constructor(public id?: number, public nome?: string, public tipoFinanceiros?: IFinanceiro[]) {}
}
