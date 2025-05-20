import { ChangeDetectionStrategy, Component, OnInit, signal } from '@angular/core';
import { Mensaje } from '../../interfaces/mensaje.interface';
import { BarraEscrituraComponent } from "../barra-escritura/barra-escritura.component";



@Component({
  selector: 'app-mensaje',
  imports: [BarraEscrituraComponent],
  templateUrl: './mensaje.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})



export class MensajeComponent implements OnInit{

  mensajes = signal<Mensaje[]>([]);

   nombre = localStorage.getItem('usuarioLogueado');

   contenidoMensaje = signal('');



  ngOnInit(): void {
    const eventSource = new EventSource("http://localhost:8081/consumeReactivo",{
      withCredentials: true
    } as any);



    eventSource.onmessage= (event)=>{
      const mensajeNuevo: Mensaje = event.data;
      console.log(mensajeNuevo);

      this.contenidoMensaje.set(mensajeNuevo.message);
      this.mensajes.update((mensajesPrevios)=>[mensajeNuevo, ...mensajesPrevios]);
    }

    eventSource.onerror = (error) => {
      console.error("Fallo: ", error);
      eventSource.close();
    }
  }


 }
