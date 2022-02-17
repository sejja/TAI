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
import { TaiSeleccionarComponent } from './tai-seleccionar/tai-seleccionar.component';
import { LoginComponent } from './login/login.component';
import { TaiResultadosComponent } from './tai-resultados/tai-resultados.component';

// npm install highcharts --save
// npm install highcharts-angular --save
import { HighchartsChartModule } from 'highcharts-angular';



@NgModule({
  declarations: [
    AppComponent,
    TestTAIComponent,
    InicioComponent,
    TaiListarComponent,
    TaiEditarComponent,
    TaiSeleccionarComponent,
    LoginComponent,
    TaiResultadosComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    HighchartsChartModule
  ],
  providers: [
    ClienteApiOrdersService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
