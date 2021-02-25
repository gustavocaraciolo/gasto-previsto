export interface ITiposFinanceiro {
  id?: number;
  nome?: string;
}

export class TiposFinanceiro implements ITiposFinanceiro {
  constructor(public id?: number, public nome?: string) {}
}
