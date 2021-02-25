export interface IAnexo {
  id?: number;
  nomeContentType?: string;
  nome?: any;
}

export class Anexo implements IAnexo {
  constructor(public id?: number, public nomeContentType?: string, public nome?: any) {}
}
