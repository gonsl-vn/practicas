import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { EnvioMensajeService } from '../../services/envioMensaje.service';
import { Mensaje } from '../../interfaces/mensaje.interface';
import { MensajeConNombreUsu } from '../../interfaces/mensajeConNombreUsu';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-barra-escritura',
  imports: [FormsModule, CommonModule ],
  templateUrl: './barra-escritura.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BarraEscrituraComponent {

  contenidoMensaje =  '';
  respuesta = '';
  nombreUsu = localStorage.getItem('usuarioLogueado') || ''

  constructor(private mensajeService: EnvioMensajeService){}
  enviarMensaje(){

    const mensaje: MensajeConNombreUsu = {
      nombreUsuario: this.nombreUsu,
      mensaje: this.contenidoMensaje,
    }
    console.log('Mensaje enviado al servicio: ', mensaje);

    this.mensajeService.enviarMensaje(mensaje).subscribe({
      next: res => {
        this.respuesta = 'Mensaje enviado con exito';
        this.contenidoMensaje  = '';
        setTimeout(()=> (this.respuesta =  ''),2000);
      }, error : err  => {
        this.respuesta = 'Error en el envio del mensaje',
        console.error('Mensaje no enviado, ', err);
      }

    });
  }

 }
