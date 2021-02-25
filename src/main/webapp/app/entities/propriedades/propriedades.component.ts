import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPropriedades } from 'app/shared/model/propriedades.model';
import { PropriedadesService } from './propriedades.service';
import { PropriedadesDeleteDialogComponent } from './propriedades-delete-dialog.component';

@Component({
  selector: 'jhi-propriedades',
  templateUrl: './propriedades.component.html',
})
export class PropriedadesComponent implements OnInit, OnDestroy {
  propriedades?: IPropriedades[];
  eventSubscriber?: Subscription;

  constructor(
    protected propriedadesService: PropriedadesService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.propriedadesService.query().subscribe((res: HttpResponse<IPropriedades[]>) => (this.propriedades = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPropriedades();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPropriedades): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPropriedades(): void {
    this.eventSubscriber = this.eventManager.subscribe('propriedadesListModification', () => this.loadAll());
  }

  delete(propriedades: IPropriedades): void {
    const modalRef = this.modalService.open(PropriedadesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.propriedades = propriedades;
  }
}
