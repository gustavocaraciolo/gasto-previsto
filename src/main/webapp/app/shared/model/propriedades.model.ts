import { IUser } from 'app/core/user/user.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoPagamento } from 'app/shared/model/enumerations/tipo-pagamento.model';

export interface IPropriedades {
  id?: number;
  nome?: string;
  moeda?: Moeda;
  dtAssContrato?: string;
  descricao?: any;
  tipoPagamento?: TipoPagamento;
  user?: IUser;
}

export class Propriedades implements IPropriedades {
  constructor(
    public id?: number,
    public nome?: string,
    public moeda?: Moeda,
    public dtAssContrato?: string,
    public descricao?: any,
    public tipoPagamento?: TipoPagamento,
    public user?: IUser
  ) {}
}
