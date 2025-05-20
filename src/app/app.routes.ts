import { Routes } from '@angular/router';
import { MensajeComponent } from './mensaje/mensaje/mensaje.component';
import { LoginComponent } from './auth/login/login.component';
import { RegistroComponent } from './auth/registro/registro.component';
import { BarraEscrituraComponent } from './mensaje/barra-escritura/barra-escritura.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch:'full'
  },
  {
    path: 'registro',
    component: RegistroComponent
  },
  {
    path: 'auth/login',
    component: LoginComponent
  },
  {
    path: 'consumeMensajes',
    component: MensajeComponent
  },
  {
    path: 'barra',
    component: BarraEscrituraComponent
  }
];
