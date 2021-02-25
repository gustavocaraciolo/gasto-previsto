import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GastoPrevistoTestModule } from '../../../test.module';
import { PropriedadesUpdateComponent } from 'app/entities/propriedades/propriedades-update.component';
import { PropriedadesService } from 'app/entities/propriedades/propriedades.service';
import { Propriedades } from 'app/shared/model/propriedades.model';

describe('Component Tests', () => {
  describe('Propriedades Management Update Component', () => {
    let comp: PropriedadesUpdateComponent;
    let fixture: ComponentFixture<PropriedadesUpdateComponent>;
    let service: PropriedadesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [PropriedadesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PropriedadesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Propriedades(123);
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
        const entity = new Propriedades();
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
