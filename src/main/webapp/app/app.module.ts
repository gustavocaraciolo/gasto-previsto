import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GastoPrevistoSharedModule } from 'app/shared/shared.module';
import { GastoPrevistoCoreModule } from 'app/core/core.module';
import { GastoPrevistoAppRoutingModule } from './app-routing.module';
import { GastoPrevistoHomeModule } from './home/home.module';
import { GastoPrevistoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    GastoPrevistoSharedModule,
    GastoPrevistoCoreModule,
    GastoPrevistoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GastoPrevistoEntityModule,
    GastoPrevistoAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class GastoPrevistoAppModule {}
