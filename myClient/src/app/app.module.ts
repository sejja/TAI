import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

//import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';

import { TaiListarComponent } from './tai-listar/tai-listar.component';
import { TestTAIComponent } from './test-tai/test-tai.component';
import { InicioComponent } from './inicio/inicio.component';
import { TaiEditarComponent } from './tai-editar/tai-editar.component'; // CLI 
import { ClienteApiOrdersService } from './shared/api-tai/cliente-api-tai.service';



@NgModule({
  declarations: [
    AppComponent,
    TestTAIComponent,
    InicioComponent,
    TaiListarComponent,
    TaiEditarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    ClienteApiOrdersService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
