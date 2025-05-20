import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Usuario } from '../../interfaces/usuario.interface';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import {FormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {

  usuario : Usuario ={
    nombre: '',
    is_prime: false,
    password: ''
  };
  mensaje = '';
  constructor(private authService: AuthService, private router: Router){}

  login(){
    this.authService.login(this.usuario).subscribe({
      next: res =>{
        localStorage.setItem('usuarioLogueado', this.usuario.nombre)
        this.mensaje = res;
        this.router.navigateByUrl('/consumeMensajes');
      },error: err => this.mensaje = err.error()
      });
  }
  goToRegistro(){
    this.router.navigateByUrl('/registro');
  }

}

