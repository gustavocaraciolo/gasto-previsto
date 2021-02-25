import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { TiposFinanceiroUpdateComponent } from 'app/entities/tipos-financeiro/tipos-financeiro-update.component';
import { TiposFinanceiroService } from 'app/entities/tipos-financeiro/tipos-financeiro.service';
import { TiposFinanceiro } from 'app/shared/model/tipos-financeiro.model';

describe('Component Tests', () => {
  describe('TiposFinanceiro Management Update Component', () => {
    let comp: TiposFinanceiroUpdateComponent;
    let fixture: ComponentFixture<TiposFinanceiroUpdateComponent>;
    let service: TiposFinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [TiposFinanceiroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TiposFinanceiroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TiposFinanceiroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TiposFinanceiroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TiposFinanceiro(123);
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
        const entity = new TiposFinanceiro();
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
