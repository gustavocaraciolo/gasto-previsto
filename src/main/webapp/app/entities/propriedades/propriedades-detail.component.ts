import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPropriedades } from 'app/shared/model/propriedades.model';

@Component({
  selector: 'jhi-propriedades-detail',
  templateUrl: './propriedades-detail.component.html',
})
export class PropriedadesDetailComponent implements OnInit {
  propriedades: IPropriedades | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ propriedades }) => (this.propriedades = propriedades));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
