import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoFinanceiro } from 'app/shared/model/enumerations/tipo-financeiro.model';

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
  tipoFinanceiro?: TipoFinanceiro;
  comprovantePagamentoContentType?: string;
  comprovantePagamento?: any;
  user?: IUser;
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
    public tipoFinanceiro?: TipoFinanceiro,
    public comprovantePagamentoContentType?: string,
    public comprovantePagamento?: any,
    public user?: IUser
  ) {}
}
