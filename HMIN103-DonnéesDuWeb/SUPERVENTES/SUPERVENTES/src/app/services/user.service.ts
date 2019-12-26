import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from '../models/user.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  selectedUser: User = {
    nom: '',
    prenom: '',
    email: '',
    mdp: ''
  };//initialiser l'objet Utilisateur avec les params vides

  noAuthHeader = {headers : new HttpHeaders({ 'NoAuth': 'True' }) };

  constructor(private http : HttpClient) { 
  
  }

  postUser(user: User) : Observable<any>{
    return this.http.post(environment.apiBaseUrl + 'inscription', user, this.noAuthHeader);
  }

  login(authCredentials) : Observable<any> {
    return this.http.post(environment.apiBaseUrl + 'authentification', authCredentials, this.noAuthHeader);
  }

  getUserProfile(){
    return this.http.get(environment.apiBaseUrl + 'profile');
  }

  //helper

  setToken(token: string){
    localStorage.setItem('token', token);
  }

  getToken(){
    return localStorage.getItem('token');
  }

  deleteToken(){
    localStorage.removeItem('token');
  }

  setAuthenticated(){
    localStorage.setItem('authenticated', 'true');
  }

  deleteAuthenticated(){
    localStorage.removeItem('authenticated');
  }

  getUserPayload(){
    var token = this.getToken();
    if(token){
      var userPayload = atob(token.split('.')[1]);
      return JSON.parse(userPayload);
    }else{
      return null;
    }
  }

  isLoggedIn(){
    var userPayload = this.getUserPayload();
    if(userPayload){
      return userPayload.exp > Date.now()/1000;
    }else{
      return false;
    }
  }
}
