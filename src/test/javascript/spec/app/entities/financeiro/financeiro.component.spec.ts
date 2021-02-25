import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GastoPrevistoTestModule } from '../../../test.module';
import { FinanceiroComponent } from 'app/entities/financeiro/financeiro.component';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';
import { Financeiro } from 'app/shared/model/financeiro.model';

describe('Component Tests', () => {
  describe('Financeiro Management Component', () => {
    let comp: FinanceiroComponent;
    let fixture: ComponentFixture<FinanceiroComponent>;
    let service: FinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [FinanceiroComponent],
      })
        .overrideTemplate(FinanceiroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinanceiroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanceiroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Financeiro(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.financeiros && comp.financeiros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
