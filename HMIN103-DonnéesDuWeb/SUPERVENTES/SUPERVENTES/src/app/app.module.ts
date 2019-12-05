//build-in
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

//routing
import { AppRoutingModule } from './app-routing.module';

//components 
import { AppComponent } from './app.component';
import { ProduitsComponent } from './produits/produits.component';
import { ConnexionComponent } from './utilisateur/connexion/connexion.component';
import { MenuComponent } from './menu/menu.component';
import { InscriptionComponent } from './utilisateur/inscription/inscription.component';
import { UtilisateurComponent } from './utilisateur/utilisateur.component';

@NgModule({
  declarations: [
    AppComponent,
    ProduitsComponent,
    ConnexionComponent,
    MenuComponent,
    InscriptionComponent,
    UtilisateurComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
