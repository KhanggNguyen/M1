import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProduitsService {
  
  constructor(private http : HttpClient) { }

  getProduits() : Observable<any> {
    return this.http.get(environment.apiBaseUrl +'produits');
  }

  getProduitsParCategorie(categorie): Observable<any>{
    return this.http.get(environment.apiBaseUrl +'produits/'+categorie);
  }

  
}
