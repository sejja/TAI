import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { TaiEditarComponent } from './tai-editar/tai-editar.component';


import { TaiListarComponent } from './tai-listar/tai-listar.component';
import { TestTAIComponent } from './test-tai/test-tai.component';


const routes: Routes = [
  {path: 'inicio', component:InicioComponent},
  {path: 'tai', component: TaiListarComponent},
  {path: 'tai/test', component:TestTAIComponent},
  {path: 'tai/new', component: TaiEditarComponent },
  {path: '**', redirectTo:'inicio', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
