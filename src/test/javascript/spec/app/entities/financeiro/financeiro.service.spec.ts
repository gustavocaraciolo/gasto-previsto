import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';
import { IFinanceiro, Financeiro } from 'app/shared/model/financeiro.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoFinanceiro } from 'app/shared/model/enumerations/tipo-financeiro.model';

describe('Service Tests', () => {
  describe('Financeiro Service', () => {
    let injector: TestBed;
    let service: FinanceiroService;
    let httpMock: HttpTestingController;
    let elemDefault: IFinanceiro;
    let expectedResult: IFinanceiro | IFinanceiro[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FinanceiroService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Financeiro(0, currentDate, currentDate, 0, 0, 0, 0, Moeda.REAL, 'AAAAAAA', TipoFinanceiro.DESPESA);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Financeiro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPagamento: currentDate,
            dataVencimento: currentDate,
          },
          returnedFromService
        );

        service.create(new Financeiro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Financeiro', () => {
        const returnedFromService = Object.assign(
          {
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT),
            valorPlanejado: 1,
            valorPago: 1,
            periodicidade: 1,
            quantidadeParcelas: 1,
            moeda: 'BBBBBB',
            descricao: 'BBBBBB',
            tipoFinanceiro: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPagamento: currentDate,
            dataVencimento: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Financeiro', () => {
        const returnedFromService = Object.assign(
          {
            dataPagamento: currentDate.format(DATE_FORMAT),
            dataVencimento: currentDate.format(DATE_FORMAT),
            valorPlanejado: 1,
            valorPago: 1,
            periodicidade: 1,
            quantidadeParcelas: 1,
            moeda: 'BBBBBB',
            descricao: 'BBBBBB',
            tipoFinanceiro: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataPagamento: currentDate,
            dataVencimento: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Financeiro', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
