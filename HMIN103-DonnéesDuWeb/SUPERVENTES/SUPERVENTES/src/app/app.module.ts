//build-in
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon'
import {FlashMessagesModule} from 'angular2-flash-messages';

//routing
import { routes } from './app-routing.module';

//components 
import { AppComponent } from './app.component';
import { ProduitsComponent } from './produits/produits.component';
import { MenuComponent } from './menu/menu.component';
import { InscriptionComponent } from './utilisateur/inscription/inscription.component';
import { UtilisateurComponent } from './utilisateur/utilisateur.component';
import { ProfileComponent } from './utilisateur/profile/profile.component';
import { AuthentificationComponent } from './utilisateur/authentification/authentification.component';
import { PanierComponent } from './panier/panier.component';

//service
import { UserService } from './services/user.service';

//other
import { AuthGuard } from './auth/auth.guard';
import { AuthInterceptor } from './auth/auth.interceptor';
import { RechercheComponent } from './recherche/recherche.component';
import { RechercheResultatComponent } from './recherche/recherche-resultat/recherche-resultat.component';

@NgModule({
  declarations: [
    AppComponent,
    ProduitsComponent,
    MenuComponent,
    InscriptionComponent,
    UtilisateurComponent,
    ProfileComponent,
    AuthentificationComponent,
    PanierComponent,
    RechercheComponent,
    RechercheResultatComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
    FlashMessagesModule.forRoot(),
    RouterModule.forRoot(routes)
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: AuthInterceptor,
    multi: true
  },
    UserService,
    AuthGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
