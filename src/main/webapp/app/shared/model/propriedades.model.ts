import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IFinanceiro } from 'app/shared/model/financeiro.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoPagamento } from 'app/shared/model/enumerations/tipo-pagamento.model';

export interface IPropriedades {
  id?: number;
  nome?: string;
  moeda?: Moeda;
  valorEstimado?: number;
  dtAssContrato?: Moment;
  descricao?: any;
  tipoPagamento?: TipoPagamento;
  user?: IUser;
  financeiro?: IFinanceiro;
  anexos?: IAnexo[];
}

export class Propriedades implements IPropriedades {
  constructor(
    public id?: number,
    public nome?: string,
    public moeda?: Moeda,
    public valorEstimado?: number,
    public dtAssContrato?: Moment,
    public descricao?: any,
    public tipoPagamento?: TipoPagamento,
    public user?: IUser,
    public financeiro?: IFinanceiro,
    public anexos?: IAnexo[]
  ) {}
}
