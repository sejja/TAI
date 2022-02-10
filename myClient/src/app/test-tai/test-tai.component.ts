import { Component, OnInit, HostListener} from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Estado } from '../shared/api-tai/app.estado-model';

@Component({
  selector: 'app-test-tai',
  templateUrl: './test-tai.component.html',
  styleUrls: ['./test-tai.component.css']
})
export class TestTAIComponent implements OnInit {


  ifase:any;
  nfase:any;

  id = 0;
  i = 0;
  j = 0;

  imagenes1: Array<Concept> = [];
  imagenes2: Array<Concept> = [];
  palabras1: Array<Concept> = [];
  palabras2: Array<Concept> = [];

  tai: Tai;
  

  constructor(private ruta: ActivatedRoute, private router: Router,
    private clienteApiRest: ClienteApiOrdersService) { }

  ngOnInit() {
    this.ifase = 0;
    this.nfase = 9;

    // Elimina los query params.
    this.router.navigate([]);

    // Operacion: va en el ultimo string (parte) de la URL


    //y se trae el json con el pedido, para mostrarlo en el
    //HTML. Si no es “editar”, será “nuevo” y la operacion de
    //traer pedido no se realizará y el formulario aparecerá vacio
    this.ruta.paramMap.subscribe( // Capturamos el id de la URL
      params => {
        this.id = Number(params.get('id'));
      },
      err => {
        console.log("Error al leer id para editar: " + err)
        throw err;
      }
    )

    this.getTai();
  }

  getTai(){
    this.clienteApiRest.getTai(this.id).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.tai = resp.body as Tai; // Se obtiene la lista de users desde la respuesta
          this.palabras1 = [];
          this.palabras2 = [];
          this.imagenes1 = [];
          this.imagenes2 = [];
          this.getLists();
        } 
      }
    );
  }

  getUrl(imagen: Concept):String{
    let url = "http://localhost:8080/uploads/";
    return url + this.tai.code + imagen.name;
  }

  getLists(){

    this.tai.concepts.forEach(element => {
      console.log("Status --> " +element.status);
      switch(element.status){
        case "Imagen1": {
          this.imagenes1.push(element)
          break;
        }
        case "Imagen2": {
          this.imagenes2.push(element)
          break;
        }
        case "Palabra1": {
          this.palabras1.push(element)
          break;
        }
        case "Palabra2": {
          this.palabras2.push(element)
          break;
        }
      }
    });

  }


  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 

    console.log("Evento --> " + this.ifase);
    console.log("NAME --> " + this.tai.concepts[0].name);
    
    if (event.key == " " && event.target == document.body) {
      event.preventDefault();
      if (this.ifase == 5){//reonocimento de imagenes

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 6) {//reonocimento de palabras

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 7) {//test 1

        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 8) {//test 2

        this.ifase = (this.ifase + 1) % this.nfase;
      } else{
        
        this.ifase = (this.ifase + 1) % this.nfase;
      }
    }

  }

}
