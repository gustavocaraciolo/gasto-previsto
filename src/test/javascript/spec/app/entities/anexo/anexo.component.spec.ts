import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GastoPrevistoTestModule } from '../../../test.module';
import { AnexoComponent } from 'app/entities/anexo/anexo.component';
import { AnexoService } from 'app/entities/anexo/anexo.service';
import { Anexo } from 'app/shared/model/anexo.model';

describe('Component Tests', () => {
  describe('Anexo Management Component', () => {
    let comp: AnexoComponent;
    let fixture: ComponentFixture<AnexoComponent>;
    let service: AnexoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [AnexoComponent],
      })
        .overrideTemplate(AnexoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnexoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnexoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Anexo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.anexos && comp.anexos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
