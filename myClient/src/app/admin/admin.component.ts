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

  constructor(private router: Router, private clienteApiAuth: ClienteApiAuthService) { } 

  ngOnInit(): void {
    this.getUsers();
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

}
