import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TipoFinanceiroComponent } from 'app/entities/tipo-financeiro/tipo-financeiro.component';
import { TipoFinanceiroService } from 'app/entities/tipo-financeiro/tipo-financeiro.service';
import { TipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';

describe('Component Tests', () => {
  describe('TipoFinanceiro Management Component', () => {
    let comp: TipoFinanceiroComponent;
    let fixture: ComponentFixture<TipoFinanceiroComponent>;
    let service: TipoFinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TipoFinanceiroComponent],
      })
        .overrideTemplate(TipoFinanceiroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFinanceiroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFinanceiroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoFinanceiro(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoFinanceiros && comp.tipoFinanceiros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
