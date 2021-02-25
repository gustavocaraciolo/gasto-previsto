import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TipoFinanceiroUpdateComponent } from 'app/entities/tipo-financeiro/tipo-financeiro-update.component';
import { TipoFinanceiroService } from 'app/entities/tipo-financeiro/tipo-financeiro.service';
import { TipoFinanceiro } from 'app/shared/model/tipo-financeiro.model';

describe('Component Tests', () => {
  describe('TipoFinanceiro Management Update Component', () => {
    let comp: TipoFinanceiroUpdateComponent;
    let fixture: ComponentFixture<TipoFinanceiroUpdateComponent>;
    let service: TipoFinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TipoFinanceiroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TipoFinanceiroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFinanceiroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFinanceiroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoFinanceiro(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoFinanceiro();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
