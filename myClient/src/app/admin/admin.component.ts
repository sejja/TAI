import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
// npm install --save export-to-csv
import { ExportToCsv } from 'export-to-csv';
import { User } from '../shared/api-auth/app.user-model';
import { ClienteApiAuthService } from '../shared/api-auth/cliente-api-auth.service';
import { TaiResult } from '../shared/api-tai/app.result-model';
import { Tai } from '../shared/api-tai/app.tai-model';
import { ClienteApiOrdersService } from '../shared/api-tai/cliente-api-tai.service';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users:User[];
  tais: Array<Tai> = [];
  resultados: TaiResult[];
  idTai: number = 0;
  tai: Tai;
  
  newUser = {
    id: 0,
    name: "",
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    enabled: true,
    createdAt: new Date,
    updatedAt: new Date
  };

  userAdd = this.newUser as User;  // Hay que darle valor inicial, si no salta una
  //userEdit = this.newUser as User;

  constructor(private router: Router, private clienteApiAuth: ClienteApiAuthService, private ruta: ActivatedRoute, 
    private clienteApiRest: ClienteApiOrdersService) { } 




  ngOnInit(): void {
    this.getUsers();
    this.getTais();
    this.getResults(this.idTai);
  }

  refresh(): void {
    window.location.reload();
  }

  getCSV(){
    //CSV OPTIONS
    var options = {
      filename: <string>this.tai.code + "-" + this.tai.imagen1 + "-vs-" + this.tai.imagen2 + "-" + this.tai.palabra1 + "-vs-" + this.tai.palabra2,
      fieldSeparator: ',',
      quoteStrings: '"',
      decimalSeparator: '.',
      showLabels: true,
      showTitle: true,
      title: <string>this.tai.code + "-" + this.tai.imagen1 + "-vs-" + this.tai.imagen2 + "-" + this.tai.palabra1 + "-vs-" + this.tai.palabra2,
      useTextFile: false,
      useBom: true,
      useKeysAsHeaders: true,
      // headers: ['Column 1', 'Column 2', etc...] <-- Won't work with useKeysAsHeaders present!
    };
    var csvExporter = new ExportToCsv(options);
    csvExporter.generateCsv(this.resultados);
  }

  // Obtiene la lista de users
  getUsers() {
    this.clienteApiAuth.getUsers().subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.users = resp.body as User[]; // Se obtiene la lista de users desde la respuesta
        } 
      },
      err => {
        console.log("Error al traer la lista de users: " + err.message);
        throw err;
      }
    )

  }

  getTais() {
    this.clienteApiRest.getTais().subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.tais = resp.body as Tai[]; // Se obtiene la lista de users desde la respuesta
        }
      },
      err => {
        console.log("Error al traer la lista de Tais: " + err.message);
        throw err;
      }
    )
  }

  getTai() {
    this.clienteApiRest.getTai(this.idTai).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.tai = resp.body as Tai; // Se obtiene la lista de users desde la respuesta
        }
      },
      err => {
        console.log("Error al traer la lista de Tais: " + err.message);
        throw err;
      }
    )
  }

  getResults(idTai: number) {
    this.idTai = idTai;
    this.clienteApiRest.getResults(this.idTai).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          this.resultados = resp.body as TaiResult[]; // Se obtiene la lista de users desde la respuesta
          this.getTai();
        }
      }
    );
  }

  onSubmit(){
    if(this.userAdd.id == 0){
      this.nuevo();
    }
  }


  nuevo() {
    this.clienteApiAuth.addUser(this.userAdd).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          //this.users = resp.body as User[]; // Se obtiene la lista de users desde la respuesta
          this.getUsers();
        }
      },
      err => {
        console.log("Error al traer la lista de users: " + err.message);
        throw err;
      }
    );

  }

  deleteUser(id : Number) {
    this.clienteApiAuth.deleteUser(id).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          //this.users = resp.body as User[]; // Se obtiene la lista de users desde la respuesta
          this.getUsers();
        }
      },
      err => {
        console.log("Error al traer la lista de users: " + err.message);
        throw err;
      }
    );
    
  }

  deleteTai(){
    this.clienteApiRest.deleteTai(this.idTai).subscribe(
      resp => {
        if (resp.status < 400) { // Si no hay error en la respuesta
          //this.users = resp.body as User[]; // Se obtiene la lista de users desde la respuesta
          this.getTais();
          this.idTai=0;
        }
      },
      err => {
        console.log("Error al traer la lista de users: " + err.message);
        throw err;
      }
    );
  }

  sendEnable(tai:Tai){
    tai.enable = !tai.enable;
    console.log(tai.enable);
    this.clienteApiRest.sendEnable(tai.id, tai.enable).subscribe();
  }

  clickLogout() {
    this.clienteApiAuth.logout();
    this.router.navigate(['inicio']);
  }

  onDeleteTaiSubmit(){}

}
