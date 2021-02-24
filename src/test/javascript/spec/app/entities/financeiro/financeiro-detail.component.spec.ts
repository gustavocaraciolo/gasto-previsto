import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GastoPrevistoTestModule } from '../../../test.module';
import { FinanceiroDetailComponent } from 'app/entities/financeiro/financeiro-detail.component';
import { Financeiro } from 'app/shared/model/financeiro.model';

describe('Component Tests', () => {
  describe('Financeiro Management Detail Component', () => {
    let comp: FinanceiroDetailComponent;
    let fixture: ComponentFixture<FinanceiroDetailComponent>;
    let dataUtils: JhiDataUtils;
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
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load financeiro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.financeiro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
