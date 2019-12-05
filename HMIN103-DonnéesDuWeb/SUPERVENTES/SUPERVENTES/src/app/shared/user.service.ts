import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private urlBase : string = 'http://localhost:3000/';

  selectedUser: User = {
    nom: '',
    prenom: '',
    email: '',
    mdp: ''
  };//initialiser l'objet Utilisateur avec les params vides

  constructor(private http : HttpClient) { 
    
  }
}
