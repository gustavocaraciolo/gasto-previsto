import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PropriedadesService } from 'app/entities/propriedades/propriedades.service';
import { IPropriedades, Propriedades } from 'app/shared/model/propriedades.model';
import { Moeda } from 'app/shared/model/enumerations/moeda.model';
import { TipoPagamento } from 'app/shared/model/enumerations/tipo-pagamento.model';

describe('Service Tests', () => {
  describe('Propriedades Service', () => {
    let injector: TestBed;
    let service: PropriedadesService;
    let httpMock: HttpTestingController;
    let elemDefault: IPropriedades;
    let expectedResult: IPropriedades | IPropriedades[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PropriedadesService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Propriedades(0, 'AAAAAAA', Moeda.REAL, 0, currentDate, 'AAAAAAA', TipoPagamento.ENTRADA);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dtAssContrato: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Propriedades', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dtAssContrato: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtAssContrato: currentDate,
          },
          returnedFromService
        );

        service.create(new Propriedades()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Propriedades', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            moeda: 'BBBBBB',
            valorEstimado: 1,
            dtAssContrato: currentDate.format(DATE_FORMAT),
            descricao: 'BBBBBB',
            tipoPagamento: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtAssContrato: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Propriedades', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            moeda: 'BBBBBB',
            valorEstimado: 1,
            dtAssContrato: currentDate.format(DATE_FORMAT),
            descricao: 'BBBBBB',
            tipoPagamento: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dtAssContrato: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Propriedades', () => {
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
