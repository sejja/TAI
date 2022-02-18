import { HttpClient, HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Auth } from './app.auth-model';
import { User } from './app.user-model';

@Injectable({
    providedIn: 'root'
})
export class ClienteApiAuthService {

    private static readonly BASE_URI = 'http://localhost:8081/login';
    private static readonly BASE_URI_USERS = 'http://localhost:8081/users';

    constructor(private http: HttpClient) { }

    // Hace un post, con una solicitud de logins
    login(auth: Auth): Observable<HttpResponse<any>> {
        let url = ClienteApiAuthService.BASE_URI;
        return this.http.post(url, auth, { observe: 'response' });
    }

    // Guarda en el navegador el token jwt
    setSession(auth: Auth) {
        localStorage.setItem('jwt_token', auth.token);
    }

    // Comprueba el usuario esta loggeado mirando
    // si existe el token jwt
    isLoggedIn() {
        let isLoggedIn = false;
        const expiration = localStorage.getItem('jwt_token');
        if (expiration) isLoggedIn = true;
        return isLoggedIn;
    }

    // Cierra la sesión, eliminando el token jwt
    // del navegador
    logout() {
        localStorage.removeItem('jwt_token');
    }


    getUsers(): Observable<HttpResponse<User[]>> {
        let url = ClienteApiAuthService.BASE_URI_USERS;
        return this.http.get<User[]>(url, { observe: 'response' });
    }


    

    

}
