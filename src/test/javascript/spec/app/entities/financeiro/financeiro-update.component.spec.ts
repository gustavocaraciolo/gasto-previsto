import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { FinanceiroUpdateComponent } from 'app/entities/financeiro/financeiro-update.component';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';
import { Financeiro } from 'app/shared/model/financeiro.model';

describe('Component Tests', () => {
  describe('Financeiro Management Update Component', () => {
    let comp: FinanceiroUpdateComponent;
    let fixture: ComponentFixture<FinanceiroUpdateComponent>;
    let service: FinanceiroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [FinanceiroUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FinanceiroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FinanceiroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanceiroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Financeiro(123);
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
        const entity = new Financeiro();
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
