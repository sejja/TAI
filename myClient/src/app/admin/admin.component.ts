import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../shared/api-auth/app.user-model';
import { ClienteApiAuthService } from '../shared/api-auth/cliente-api-auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  users:User[];
  
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

  constructor(private router: Router, private clienteApiAuth: ClienteApiAuthService) { } 

  ngOnInit(): void {
    this.getUsers();
  }

  refresh(): void {
    window.location.reload();
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



  delete(id : Number) {
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

}
