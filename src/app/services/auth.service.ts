import { Injectable } from '@angular/core';
import { Usuario } from '../interfaces/usuario.interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url:string = 'http://localhost:8081'

  constructor(private http: HttpClient) { }

  register(usuario:Usuario){
    return this.http.post(`${this.url}/auth/registro`,
       usuario, {responseType:'text'});
  }
  login(usuario: Usuario){
    return this.http.post(`${this.url}/auth/login`,usuario, {responseType:'text', withCredentials: true})
  }

}
