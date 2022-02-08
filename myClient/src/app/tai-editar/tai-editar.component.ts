import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Tai } from '../shared/api-tai/app.tai-model';
import { Concept } from '../shared/api-tai/app.concept-model';

import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-tai-editar',
  templateUrl: './tai-editar.component.html',
  styleUrls: ['./tai-editar.component.css']
})
export class TaiEditarComponent implements OnInit {

  selectedFile:File;
  image:string;

  newTai = {

    id: 0,
    name: "",
    palabra1: "",
    palabra2: "",
    imagen1: "",
    imagen2: "",
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

  onChange(event:Event) {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      console.log("FileUpload -> files", fileList);
      Array.from(fileList).forEach(file => this.selectedFile = file);
    }
  }
/*
  selectFile(event: Event) {
    this.selectedFiles = event.target.files;
  }

*/

  upload() {
    this.clienteApiRest.upload(this.selectedFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          //this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          //this.message = event.body.message;
          //this.fileInfos = this.uploadService.getFiles();
        }
      },
      err => {
        //this.progress = 0;
        //this.message = 'Could not upload the file!';
        //this.currentFile = undefined;
      });
    //this.selectedFiles = undefined;
  }

  deleteConcept(i: number) {
    this.tai.concepts.splice(i, 1);
  }

}
