export interface ITipoFinanceiro {
  id?: number;
  nome?: string;
}

export class TipoFinanceiro implements ITipoFinanceiro {
  constructor(public id?: number, public nome?: string) {}
}
