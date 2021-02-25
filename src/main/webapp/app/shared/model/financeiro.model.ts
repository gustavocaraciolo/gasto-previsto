import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IAnexo } from 'app/shared/model/anexo.model';
import { ITipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';
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
  anexos?: IAnexo[];
  tipoFinanceiro?: ITipoFinanceiro;
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
    public anexos?: IAnexo[],
    public tipoFinanceiro?: ITipoFinanceiro
  ) {}
}
