import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params  } from '@angular/router';
import { NgForm } from "@angular/forms";
import { FlashMessagesService } from 'angular2-flash-messages';

import { RechercheService } from '../../services/recherche.service';
import { UserService } from '../../services/user.service';
import { PanierService } from '../../services/panier.service';

@Component({
  selector: 'app-recherche-resultat',
  templateUrl: './recherche-resultat.component.html',
  styleUrls: ['./recherche-resultat.component.css']
})
export class RechercheResultatComponent implements OnInit {
  produits : Object[] = new Array();
  constructor(
    private router : Router,
    private rechercheService : RechercheService,
    private userService : UserService,
    private panierService: PanierService,
    private flashMessage: FlashMessagesService,
  ) { }

  ngOnInit() {
    if(this.rechercheService.rechercheAction){
      this.produits = this.rechercheService.resultatRecherche;
      this.rechercheService.rechercheAction = false;
      this.rechercheService.resultatRecherche = [];
    }else{
      this.router.navigateByUrl('/produits');
    }
  }

  onAjout(form: NgForm, nomP: string, typeP: string, prixP){
    let id = JSON.parse(atob(this.userService.getToken().split('.')[1]))._id;
    let produitPanier = {
      userId: id,
      nomProduit: nomP,
      type: typeP,
      prix: prixP,
      quantite: form.value.quantite
    };
    this.panierService.ajoutProduitPanier(produitPanier).subscribe(
      res => {
        if(res){
          this.flashMessage.show('Vous avez ajouté un produit dans votre panier !', {cssClass: 'alert-success', timeout: 2000});
        }
      }
    );
  }

}
