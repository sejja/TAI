import { Component, OnInit, HostListener } from '@angular/core';

import { ActivatedRoute, Params, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { TaiResponse } from '../shared/api-tai/app.response-model';
import { TaiResult } from '../shared/api-tai/app.result-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

import * as Highcharts from 'highcharts'; //npm install highcharts

@Component({
  selector: 'app-tai-resultados',
  templateUrl: './tai-resultados.component.html',
  styleUrls: ['./tai-resultados.component.css']
})
export class TaiResultadosComponent implements OnInit {

  idTai = 0;
  idResp = 0;

  resultado: TaiResult;

  constructor(private ruta: ActivatedRoute, private router: Router,
    private clienteApiRest: ClienteApiOrdersService) { }

  ngOnInit(): void {

    let obj = this.ruta.snapshot.paramMap.get('id');
    this.idTai = parseInt((obj == null) ? "null" : obj.toString());

    obj = this.ruta.snapshot.paramMap.get('id2');
    this.idResp = parseInt((obj == null) ? "null" : obj.toString());

    this.getResult();
  }

  getResult(){
    this.clienteApiRest.getResult(this.idTai, this.idResp).subscribe(
      resp =>{
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.resultado = resp.body as TaiResult; // Se obtiene la lista de users desde la respuesta
        } 
      }/*
      err => {
        console.log("Error al leer resultados " + err)
        throw err;
      }*/
    );
  }

}
