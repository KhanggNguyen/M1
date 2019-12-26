import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProduitsComponent } from './produits/produits.component';
import { UtilisateurComponent } from './utilisateur/utilisateur.component';
import { InscriptionComponent } from './utilisateur/inscription/inscription.component';
import { AuthentificationComponent } from './utilisateur/authentification/authentification.component';
import { ProfileComponent } from './utilisateur/profile/profile.component';
import { AuthGuard } from './auth/auth.guard';
import { PanierComponent } from './panier/panier.component';

export const routes: Routes = [
  {
    path : 'produits',
    component: ProduitsComponent,
  },
  {
    path: 'produits/:categorie',
    component: ProduitsComponent
  },
  {
    path: 'categories',
    component: ProduitsComponent
  },
  {
    path: 'inscription', component: UtilisateurComponent,
    children: [{ path : '', component: InscriptionComponent }]
  },
  {
    path: 'authentification', component: UtilisateurComponent,
    children: [{ path : '', component: AuthentificationComponent }]
  },
  {
    path: 'profile', component: ProfileComponent, canActivate:[AuthGuard]
  },
  {
    path: 'panier', component: PanierComponent, canActivate:[AuthGuard]
  },
  {
    path: 'supprimerProduitPanier/:id', component: PanierComponent, canActivate:[AuthGuard]
  },
  {
    path: '', redirectTo: 'produits', pathMatch:'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
