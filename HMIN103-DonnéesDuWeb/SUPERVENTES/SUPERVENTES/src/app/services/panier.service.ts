import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';

import { ProduitPanier } from '../models/produitPanier.model';

@Injectable({
  providedIn: 'root'
})
export class PanierService {

  constructor( private http : HttpClient) { }

  ajoutProduitPanier(produitPanier: object) :  Observable<any>{
    return this.http.post(environment.apiBaseUrl + 'ajoutProduitPanier', produitPanier);
  }

  getProduitPanier(id: String) :  Observable<any>{
    return this.http.get(environment.apiBaseUrl + 'getPanier/' + id);
  }

  supprimeProduitPanier(id: String) : Observable<any>{
    return this.http.get(environment.apiBaseUrl + 'supprimeProduitPanier/'+ id);
  }
}
