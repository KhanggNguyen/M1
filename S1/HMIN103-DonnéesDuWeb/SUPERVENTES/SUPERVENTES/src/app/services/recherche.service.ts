import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Produit } from '../models/produit.model';

@Injectable({
  providedIn: 'root'
})
export class RechercheService {
  rechercheAction = false;
  resultatRecherche : Object[] = new Array();
  constructor(private http : HttpClient) { }

  getProduitsByCriteres(produit : Produit) : Observable<any>{
    return this.http.post(environment.apiBaseUrl + 'recherche', produit);
  }
}
