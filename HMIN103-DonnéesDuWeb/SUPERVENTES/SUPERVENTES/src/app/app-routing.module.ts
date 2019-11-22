import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProduitsComponent } from './produits/produits.component';
import { InscriptionComponent } from './inscription/inscription.component';


const routes: Routes = [
  {
    path : 'produits',
    component: ProduitsComponent
  },
  {
    path : 'inscription',
    component: InscriptionComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
