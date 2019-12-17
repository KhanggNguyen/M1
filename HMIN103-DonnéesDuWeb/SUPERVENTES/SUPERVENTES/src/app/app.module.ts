//build-in
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon'

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

//service
import { UserService } from './shared/user.service';

//other
import { AuthGuard } from './auth/auth.guard';
import { AuthInterceptor } from './auth/auth.interceptor';
import { PanierComponent } from './panier/panier.component';

@NgModule({
  declarations: [
    AppComponent,
    ProduitsComponent,
    MenuComponent,
    InscriptionComponent,
    UtilisateurComponent,
    ProfileComponent,
    AuthentificationComponent,
    PanierComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    MatIconModule,
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
