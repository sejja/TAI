import { Component, OnInit, HostListener} from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { TaiResponse } from '../shared/api-tai/app.response-model';
import { Element } from '../shared/api-tai/app.element-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-test-tai',
  templateUrl: './test-tai.component.html',
  styleUrls: ['./test-tai.component.css']
})
export class TestTAIComponent implements OnInit {

  time = 150;

  keySig = " ";
  keyIzq = "f";
  keyDch = "j";

  ifase = 0;
  nfase = 20;//son 19 reaes
  ironda = 0;
  nronda = 5;//son 20 reales

  id = 0;

  imagenes1: Array<Concept> = [];
  imagenes2: Array<Concept> = [];
  palabras1: Array<Concept> = [];
  palabras2: Array<Concept> = [];

  tai: Tai;

  conceptTest: Concept;
  error = false;

  init:Date = new Date();
  end: Date = new Date();

  newResponse = {
    id: 0,
    codeEnc: "",
    idTai: 0,
    resp: []

  };

  response = this.newResponse as TaiResponse;  // Hay que darle valor inicial, si no salta una

  newElement = {

    tipo: "",
    correcta: true,
    tiempo: 0

  };

  element = this.newElement as Element;  // Hay que darle valor inicial, si no salta una

  idRespuesta = 0;
  

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
    this.startTimer()
    this.init = new Date();
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
    let aux1 = this.imagenes1.concat(this.imagenes2);
    let aux2 = this.palabras1.concat(this.palabras2);
    let aux3 = aux1.concat(aux2);
    this.conceptTest = aux3[this.randomIntFromInterval(0, aux3.length - 1)]
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

    this.end = new Date();

    console.log("TRespuesta", this.end.getTime() - this.init.getTime())


    if (event.key == this.keySig && event.target == document.body) {
      event.preventDefault();
      if (this.ifase < 5){

        this.timeLeft = this.time;
        this.error = false;
        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 5) {

        this.timeLeft = this.time;
        this.error = false;
        this.randomImagen();
        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 7) {

        this.timeLeft = this.time;
        this.error = false;
        this.randomPalabra();
        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 9 || this.ifase == 11) {

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 13) {

        this.timeLeft = this.time;
        this.error = false;
        this.randomImagen();
        this.ifase = (this.ifase + 1) % this.nfase;
      } else if (this.ifase == 15 || this.ifase == 17) {

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ifase = (this.ifase + 1) % this.nfase;
      }
    }



    if (event.key == this.keyIzq && event.target == document.body && this.timeLeft == 0) {
      this.end = new Date();
      if (this.ifase == 6 && this.conceptTest.status == "Imagen1"){
        this.timeLeft = this.time;
        this.error = false;
        this.randomImagen();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if (this.ifase == 8 && this.conceptTest.status == "Palabra1") {
        this.timeLeft = this.time;
        this.error = false;
        this.randomPalabra();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if ((this.ifase == 10 || this.ifase == 12) && 
        (this.conceptTest.status == "Palabra1" || this.conceptTest.status == "Imagen1")) {

        this.element.correcta = this.error;
        this.element.tiempo = this.end.getTime() - this.init.getTime();
        this.element.tipo = 'Test1';
        this.response.resp.push(this.element);
        this.element = <Element>{tipo: "",correcta: true,tiempo: 0};

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if (this.ifase == 14 && this.conceptTest.status == "Imagen2") {
        this.timeLeft = this.time;
        this.error = false;
        this.randomImagen();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if ((this.ifase == 16 || this.ifase == 18) && 
        (this.conceptTest.status == "Imagen2" || this.conceptTest.status == "Palabra1")) {

        this.element.correcta = this.error;
        this.element.tiempo = this.end.getTime() - this.init.getTime();
        this.element.tipo = 'Test2';
        this.response.resp.push(this.element);
        this.element = <Element>{ tipo: "", correcta: true, tiempo: 0 };

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ironda++;
        if (this.ironda == this.nronda) {
          if (this.ifase == 18){
            this.enviarRespuesta();
          }
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else {
        this.error = true;
      }
    }



    if (event.key == this.keyDch && event.target == document.body && this.timeLeft == 0) {
      this.end = new Date();
      if (this.ifase == 6 && this.conceptTest.status == "Imagen2") {
        this.timeLeft = this.time;

        this.error = false;
        this.randomImagen();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if (this.ifase == 8 && this.conceptTest.status == "Palabra2") {
        this.timeLeft = this.time;
        this.error = false;
        this.randomPalabra();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if ((this.ifase == 10 || this.ifase == 12) &&
        (this.conceptTest.status == "Palabra2" || this.conceptTest.status == "Imagen2")) {

        this.element.correcta = this.error;
        this.element.tiempo = this.end.getTime() - this.init.getTime();
        this.element.tipo = 'Test1';
        this.response.resp.push(this.element);
        this.element = <Element>{ tipo: "", correcta: true, tiempo: 0 };

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if (this.ifase == 14 && this.conceptTest.status == "Imagen1") {
        this.timeLeft = this.time;
        this.error = false;
        this.randomImagen();
        this.ironda++;
        if (this.ironda == this.nronda) {
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else if ((this.ifase == 16 || this.ifase == 18) &&
        (this.conceptTest.status == "Imagen1" || this.conceptTest.status == "Palabra2")) {

        this.element.correcta = this.error;
        this.element.tiempo = this.end.getTime() - this.init.getTime();
        this.element.tipo = 'Test2';
        this.response.resp.push(this.element);
        this.element = <Element>{ tipo: "", correcta: true, tiempo: 0 };

        this.timeLeft = this.time;
        this.error = false;
        this.randomConcept();
        this.ironda++;
        if (this.ironda == this.nronda) {
          if (this.ifase == 18) {
            this.enviarRespuesta();
          }
          this.ifase = (this.ifase + 1) % this.nfase;
          this.ironda = 0;
        }
      } else {
        this.error = true;
       }
    }
  }

  enviarRespuesta(){
    this.response.codeEnc = this.tai.code;
    this.response.idTai = this.tai.id;
    this.clienteApiRest.sendResponse(this.id, this.response).subscribe(
      resp => {
        let str = resp.body as String;
        console.log("String: "+str);
        console.log("TAI: ", this.tai.id);
        console.log("TAI: ", this.tai.id.toString());
        this.router.navigate(["tai/" + this.tai.id + "/resultados/" + str]);
      },
      err => {
        console.log("Error al enviar: " + err.message);
        throw err;
      }
    )
    
  }


  timeLeft: number = this.time;
  interval: any;
  aux = false;

  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
        this.aux = true;
      } else if (this.aux) {
        this.init = new Date();
        console.log(this.init);
        this.aux = false;
        this.timeLeft = 0;
        console.log(this.response.resp.toString());
      }
    }, 1)
  }

  pauseTimer() {
    clearInterval(this.interval);
  }

}
