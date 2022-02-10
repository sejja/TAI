import { Component, OnInit, HostListener} from '@angular/core';

import { ActivatedRoute, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-test-tai',
  templateUrl: './test-tai.component.html',
  styleUrls: ['./test-tai.component.css']
})
export class TestTAIComponent implements OnInit {


  ifase:any;
  nfase:any;

  id = 0;

  tai:Tai;
  

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
        } 
      }
    );
  }


  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) { 

    console.log("Evento --> " + this.ifase);
    
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
