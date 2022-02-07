import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';


@Component({
  selector: 'app-tai-editar',
  templateUrl: './tai-editar.component.html',
  styleUrls: ['./tai-editar.component.css']
})
export class TaiEditarComponent implements OnInit {


  newTai = {

    id: 0,
    name: "",
    Palabra1: "",
    Palabra2: "",
    Imagen1: "",
    Imagen2: "",
    concepts: []

  };

  tai = this.newTai as Tai;  // Hay que darle valor inicial, si no salta una

  newConcept = {

    name: "",
    status: ""

  };

  concept = this.newConcept as Concept;  // Hay que darle valor inicial, si no salta una

  constructor(private ruta: ActivatedRoute, private router: Router,
    private clienteApiRest: ClienteApiOrdersService) { }

  ngOnInit(): void {
  }

  //Se ejecuta al realizar en el template la acción de enviar el formulario
  onTaiSubmit() {
    
    this.addEncuesta();
    
  }

  // Agrega un nuevo pedido
  addEncuesta() {
    this.clienteApiRest.addOrder(this.tai).subscribe(
      resp => {
        this.router.navigate(['testTAI']);
      },
      err => {
        console.log("Error al editar: " + err.message);
        throw err;
      }
    )
  }

  
  

  onImagenSubmit() {

    this.tai.concepts.push(this.concept);
    this.concept = <Concept>{
      name: "",
      status: ""
    };
  }

  onPalabraSubmit() { 

    this.tai.concepts.push(this.concept);
    this.concept = <Concept>{
      name: "",
      status: ""
    };
  }



  deleteConcept(i: number) {
    this.tai.concepts.splice(i, 1);
  }

}
