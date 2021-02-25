import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { FinanceiroDetailComponent } from 'app/entities/financeiro/financeiro-detail.component';
import { Financeiro } from 'app/shared/model/financeiro.model';

describe('Component Tests', () => {
  describe('Financeiro Management Detail Component', () => {
    let comp: FinanceiroDetailComponent;
    let fixture: ComponentFixture<FinanceiroDetailComponent>;
    const route = ({ data: of({ financeiro: new Financeiro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [FinanceiroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FinanceiroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinanceiroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load financeiro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.financeiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
