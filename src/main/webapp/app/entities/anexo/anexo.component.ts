import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnexo } from 'app/shared/model/anexo.model';
import { AnexoService } from './anexo.service';
import { AnexoDeleteDialogComponent } from './anexo-delete-dialog.component';

@Component({
  selector: 'jhi-anexo',
  templateUrl: './anexo.component.html',
})
export class AnexoComponent implements OnInit, OnDestroy {
  anexos?: IAnexo[];
  eventSubscriber?: Subscription;

  constructor(
    protected anexoService: AnexoService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.anexoService.query().subscribe((res: HttpResponse<IAnexo[]>) => (this.anexos = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAnexos();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAnexo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInAnexos(): void {
    this.eventSubscriber = this.eventManager.subscribe('anexoListModification', () => this.loadAll());
  }

  delete(anexo: IAnexo): void {
    const modalRef = this.modalService.open(AnexoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anexo = anexo;
  }
}
