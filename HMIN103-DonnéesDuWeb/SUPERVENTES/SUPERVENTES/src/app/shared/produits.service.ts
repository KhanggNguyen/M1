import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProduitsService {
  private urlBase : string = 'http://localhost:3000/';
  
  constructor(private http : HttpClient) { }

  getProduits() : Observable<any> {
    return this.http.get(this.urlBase+'produits');
  }

  getProduitsParCategorie(categorie): Observable<any>{
    return this.http.get(this.urlBase+'produits/'+categorie);
  }

  getCategories() : Observable <any> {
    return this.http.get(this.urlBase+'categories');
  }
}
