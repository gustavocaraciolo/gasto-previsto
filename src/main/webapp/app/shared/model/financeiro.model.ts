import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { ITiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { CategoriaFinanceiro } from 'app/shared/model/enumerations/categoria-financeiro.model';

export interface IFinanceiro {
  id?: number;
  dataPagamento?: Moment;
  dataVencimento?: Moment;
  valorPlanejado?: number;
  valorPago?: number;
  periodicidade?: number;
  quantidadeParcelas?: number;
  moeda?: Moeda;
  descricao?: string;
  categoriaFinanceiro?: CategoriaFinanceiro;
  user?: IUser;
  financeiro?: ITiposFinanceiro;
  anexos?: IAnexo[];
}

export class Financeiro implements IFinanceiro {
  constructor(
    public id?: number,
    public dataPagamento?: Moment,
    public dataVencimento?: Moment,
    public valorPlanejado?: number,
    public valorPago?: number,
    public periodicidade?: number,
    public quantidadeParcelas?: number,
    public moeda?: Moeda,
    public descricao?: string,
    public categoriaFinanceiro?: CategoriaFinanceiro,
    public user?: IUser,
    public financeiro?: ITiposFinanceiro,
    public anexos?: IAnexo[]
  ) {}
}
