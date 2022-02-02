import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';


import { TestAtencionComponent } from './test-atencion/test-atencion.component';
import { TestTAIComponent } from './test-tai/test-tai.component';


const routes: Routes = [
  {path: 'inicio', component:InicioComponent},
  {path: 'testAtencion', component:TestAtencionComponent},
  {path: 'testTAI/enable', component:TestTAIComponent},
  
  {path: '**', redirectTo:'inicio', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
