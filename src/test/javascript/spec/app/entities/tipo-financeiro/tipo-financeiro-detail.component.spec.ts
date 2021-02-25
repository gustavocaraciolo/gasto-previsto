import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TipoFinanceiroDetailComponent } from 'app/entities/tipo-financeiro/tipo-financeiro-detail.component';
import { TipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';

describe('Component Tests', () => {
  describe('TipoFinanceiro Management Detail Component', () => {
    let comp: TipoFinanceiroDetailComponent;
    let fixture: ComponentFixture<TipoFinanceiroDetailComponent>;
    const route = ({ data: of({ tipoFinanceiro: new TipoFinanceiro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TipoFinanceiroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TipoFinanceiroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoFinanceiroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoFinanceiro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoFinanceiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
