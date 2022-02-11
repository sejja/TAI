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

  keySig = " ";
  keyIzq = "f";
  keyDch = "j";

  ifase = 0;
  nfase = 17;
  ironda = 0;
  nronda = 5;

  id = 0;

  imagenes1: Array<Concept> = [];
  imagenes2: Array<Concept> = [];
  palabras1: Array<Concept> = [];
  palabras2: Array<Concept> = [];

  tai: Tai;

  conceptTest: Concept;
  

  constructor(private ruta: ActivatedRoute, private router: Router,
    private clienteApiRest: ClienteApiOrdersService) { }

  ngOnInit() {

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
          this.randomPalabra();
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

  randomIntFromInterval(min: any, max: any): number { // min and max included 
    return Math.floor(Math.random() * (max - min + 1) + min);
  }

  randomConcept(){
    this.conceptTest;
  }

  randomImagen() {
    let aux = this.imagenes1.concat(this.imagenes2);
    this.conceptTest = aux[this.randomIntFromInterval(0,aux.length-1)]
  }
  randomPalabra() {
    let aux = this.palabras1.concat(this.palabras2);
    this.conceptTest = aux[this.randomIntFromInterval(0, aux.length-1)]
  }


  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 

    console.log("Fase --> " + this.ifase);
    console.log("Ronda --> " + this.ironda);

    this.ifase = (this.ifase + 1) % this.nfase;

/*    
    if (event.key == this.keySig && event.target == document.body) {
      event.preventDefault();
      if (this.ifase == 5){//reonocimento de imagenes

        if (this.ironda == this.nronda){
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0
        }
      } else if (this.ifase == 6) {//reonocimento de palabras

        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0
        }
      } else if (this.ifase == 7) {//test 1

        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0
        }
      } else if (this.ifase == 8) {//test 2

        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0
        }
      } else{
        
        this.ifase = (this.ifase + 1) % this.nfase;
      }
    }

    if (event.key == this.keyIzq && event.target == document.body) {
      if (this.ifase == 5 && this.ironda < this.nronda) {//es correcta da la de izq en fase 5
        this.ironda = (this.ironda + 1);

      }
      if (this.ifase == 6 && this.ironda < this.nronda) {//es correcta da la de izq en fase 6
        this.ironda = (this.ironda + 1);
        this.randomPalabra();

      }
      if (this.ifase == 7 && this.ironda < this.nronda) {//es correcta da la de izq en fase 7
        this.ironda = (this.ironda + 1);

      }
      if (this.ifase == 8 && this.ironda < this.nronda) {//es correcta da la de izq en fase 8
        this.ironda = (this.ironda + 1);

      }
    }
    
*/
  }

}
