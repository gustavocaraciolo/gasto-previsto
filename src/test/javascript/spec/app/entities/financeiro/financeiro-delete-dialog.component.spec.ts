import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GastoPrevistoTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { FinanceiroDeleteDialogComponent } from 'app/entities/financeiro/financeiro-delete-dialog.component';
import { FinanceiroService } from 'app/entities/financeiro/financeiro.service';

describe('Component Tests', () => {
  describe('Financeiro Management Delete Component', () => {
    let comp: FinanceiroDeleteDialogComponent;
    let fixture: ComponentFixture<FinanceiroDeleteDialogComponent>;
    let service: FinanceiroService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GastoPrevistoTestModule],
        declarations: [FinanceiroDeleteDialogComponent],
      })
        .overrideTemplate(FinanceiroDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FinanceiroDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FinanceiroService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
