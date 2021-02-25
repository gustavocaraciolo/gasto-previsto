import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoPagamento } from 'app/shared/model/enumerations/tipo-pagamento.model';

export interface IPropriedades {
  id?: number;
  nome?: string;
  moeda?: Moeda;
  dtAssContrato?: Moment;
  descricao?: any;
  tipoPagamento?: TipoPagamento;
  user?: IUser;
  anexos?: IAnexo[];
}

export class Propriedades implements IPropriedades {
  constructor(
    public id?: number,
    public nome?: string,
    public moeda?: Moeda,
    public dtAssContrato?: Moment,
    public descricao?: any,
    public tipoPagamento?: TipoPagamento,
    public user?: IUser,
    public anexos?: IAnexo[]
  ) {}
}
