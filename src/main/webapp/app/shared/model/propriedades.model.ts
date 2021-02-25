import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';

export interface IPropriedades {
  id?: number;
  nome?: string;
  moeda?: Moeda;
  valorEstimado?: number;
  dtAssContrato?: Moment;
  descricao?: any;
  user?: IUser;
  propriedades?: ITipoFinanceiro;
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
    public user?: IUser,
    public propriedades?: ITipoFinanceiro,
    public anexos?: IAnexo[]
  ) {}
}
