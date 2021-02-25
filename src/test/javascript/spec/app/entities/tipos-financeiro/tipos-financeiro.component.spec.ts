import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TiposFinanceiroComponent } from 'app/entities/tipos-financeiro/tipos-financeiro.component';
import { TiposFinanceiroService } from 'app/entities/tipos-financeiro/tipos-financeiro.service';
import { TiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';

describe('Component Tests', () => {
  describe('TiposFinanceiro Management Component', () => {
    let comp: TiposFinanceiroComponent;
    let fixture: ComponentFixture<TiposFinanceiroComponent>;
    let service: TiposFinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TiposFinanceiroComponent],
      })
        .overrideTemplate(TiposFinanceiroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiposFinanceiroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiposFinanceiroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TiposFinanceiro(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tiposFinanceiros && comp.tiposFinanceiros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
