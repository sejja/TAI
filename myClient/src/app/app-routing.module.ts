import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { LoginComponent } from './login/login.component';
import { TaiEditarComponent } from './tai-editar/tai-editar.component';
import { TaiListarComponent } from './tai-listar/tai-listar.component';
import { TaiResultadosComponent } from './tai-resultados/tai-resultados.component';
import { TaiSeleccionarComponent } from './tai-seleccionar/tai-seleccionar.component';
import { TestTAIComponent } from './test-tai/test-tai.component';

import { LoggedGuard } from './logged-guard.guard';
import { NotLoggedGuard } from './not-logged.guard'


const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'inicio', component: InicioComponent},
  { path: 'tai', component: TaiListarComponent},
  { path: 'tai/selet', component: TaiSeleccionarComponent},
  {path: 'tai/new', component: TaiEditarComponent},
  {path: 'tai/:id/test', component: TestTAIComponent },
  {path: 'tai/:id/resultados/:id2', component: TaiResultadosComponent },
  {path: '**', redirectTo:'inicio', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
