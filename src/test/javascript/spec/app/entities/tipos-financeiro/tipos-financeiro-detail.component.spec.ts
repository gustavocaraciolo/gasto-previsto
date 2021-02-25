import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TiposFinanceiroDetailComponent } from 'app/entities/tipos-financeiro/tipos-financeiro-detail.component';
import { TiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';

describe('Component Tests', () => {
  describe('TiposFinanceiro Management Detail Component', () => {
    let comp: TiposFinanceiroDetailComponent;
    let fixture: ComponentFixture<TiposFinanceiroDetailComponent>;
    const route = ({ data: of({ tiposFinanceiro: new TiposFinanceiro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TiposFinanceiroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(TiposFinanceiroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TiposFinanceiroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tiposFinanceiro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tiposFinanceiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
