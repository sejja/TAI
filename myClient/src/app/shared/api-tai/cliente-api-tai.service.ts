import { HttpClient, HttpEvent, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tai } from './app.tai-model';

@Injectable({
    providedIn: 'root'
})
export class ClienteApiOrdersService {

    private static readonly BASE_URI = 'http://localhost:8080/tai/';

    constructor(private http: HttpClient) { }

    /**
     * Obtiene una respuesta http con la lista de pedidos
     * de la peticion get http
     * @returns Respuesta http con la lista de pedidos
     */
    getTais(): Observable<HttpResponse<Tai[]>> {
        let url = ClienteApiOrdersService.BASE_URI;
        return this.http.get<Tai[]>(url, { observe: 'response' });
    }

    /**
     * Obtiene una respuesta http con un pedido por su identificador
     * @param id Identificador de un pedido
     * @returns Respuesta http con un pedido
     */
    getCode(): Observable<HttpResponse<Tai>> {
        let url = ClienteApiOrdersService.BASE_URI + "code";
        return this.http.get<Tai>(url, { observe: 'response' });
    }

    /**
     * Añade un pedido
     * @param order Pedido a añadir
     * @returns Respuesta http
     */
    addOrder(order: Tai): Observable<HttpResponse<any>> {
        let url = ClienteApiOrdersService.BASE_URI;
        return this.http.post(url, order, { observe: 'response', responseType: 'text' });
    }

    /**
     * Actualiza los datos de un pedido
     * @param id Identificador de un pedido
     * @param order Pedido modificado
     * @returns Respuesta http 
     */
    updateOrder(id: String, order: Tai): Observable<HttpResponse<any>> {
        let url = ClienteApiOrdersService.BASE_URI + id;
        return this.http.put(url, order, { observe: 'response', responseType: 'text' });
    }

    /**
     * Borra un pedido a partir de su identificador
     * @param id Identificador de un pedido
     * @returns Respuesta http
     */
    deleteOrder(id: Number): Observable<HttpResponse<any>> {
        let url = ClienteApiOrdersService.BASE_URI + id;
        return this.http.delete(url, { observe: 'response', responseType: 'text' });
    }




    

    upload(file: File, code: String): Observable<HttpEvent<any>> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        const req = new HttpRequest('POST', ClienteApiOrdersService.BASE_URI + "/upload/" + code, formData, {
            reportProgress: true,
            responseType: 'text'
        });
        return this.http.request(req);
    }

    /*getFiles(): Observable<any> {
        return this.http.get(ClienteApiOrdersService.BASE_URI + "/files");
    }*/

}
