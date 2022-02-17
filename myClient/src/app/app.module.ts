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
import { ClienteApiAuthService } from './shared/api-auth/cliente-api-auth.service';
import { TaiSeleccionarComponent } from './tai-seleccionar/tai-seleccionar.component';
import { LoginComponent } from './login/login.component';
import { TaiResultadosComponent } from './tai-resultados/tai-resultados.component';

import { LoggedGuard } from './logged-guard.guard';
import { NotLoggedGuard } from './not-logged.guard';

import { JwtInterceptor } from './jwt.interceptor';

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
    ClienteApiOrdersService,
    ClienteApiAuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
