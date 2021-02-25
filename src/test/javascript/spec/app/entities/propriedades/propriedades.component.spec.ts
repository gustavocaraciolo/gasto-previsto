import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GastoPrevistoTestModule } from '../../../test.module';
import { PropriedadesComponent } from 'app/entities/propriedades/propriedades.component';
import { PropriedadesService } from 'app/entities/propriedades/propriedades.service';
import { Propriedades } from 'app/shared/model/propriedades.model';

describe('Component Tests', () => {
  describe('Propriedades Management Component', () => {
    let comp: PropriedadesComponent;
    let fixture: ComponentFixture<PropriedadesComponent>;
    let service: PropriedadesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [PropriedadesComponent],
      })
        .overrideTemplate(PropriedadesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Propriedades(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.propriedades && comp.propriedades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
