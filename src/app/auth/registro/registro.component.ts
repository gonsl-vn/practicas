import { ChangeDetectionStrategy, Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Usuario } from '../../interfaces/usuario.interface';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-registro',
  imports: [FormsModule, CommonModule],
  templateUrl: './registro.component.html',
})
export class RegistroComponent {

  usuario: Usuario = {
    nombre :'',
    is_prime: false,
    password: ''
  };
  mensaje = '';

  constructor(private authService:AuthService, private router: Router){}

  registrar(){
    this.authService.register(this.usuario).subscribe({
      next: res =>{
        console.log("Bieeen entra en registrar()");

        this.mensaje  = res,
        this.router.navigateByUrl('/auth/login')
      },error : err => this.mensaje = err.console.error()

    });
  }
  goToLogin(){
    console.log("Metodo goToLogin Llamado");

    this.router.navigateByUrl('/auth/login')
  }
}
