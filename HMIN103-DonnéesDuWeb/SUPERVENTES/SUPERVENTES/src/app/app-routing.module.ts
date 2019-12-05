import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProduitsComponent } from './produits/produits.component';
import { UtilisateurComponent } from './utilisateur/utilisateur.component';
import { InscriptionComponent } from './utilisateur/inscription/inscription.component';


const routes: Routes = [
  {
    path : 'produits',
    component: ProduitsComponent
  },
  {
    path: 'inscription', component: UtilisateurComponent,
    children: [{ path : '', component: InscriptionComponent }]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
