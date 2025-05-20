import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MensajeConNombreUsu } from '../interfaces/mensajeConNombreUsu';

@Injectable({
  providedIn: 'root'
})
export class EnvioMensajeService {

  private url:string = 'http://localhost:8081'


  constructor(private http: HttpClient) { }
  enviarMensaje(mensajeAEnviar: MensajeConNombreUsu){
    console.log('mensaje recibido en servicio: ', mensajeAEnviar);

    return this.http.post(`${this.url}/enviaMensaje`, mensajeAEnviar, {responseType:'text', withCredentials:true});
  }
}
