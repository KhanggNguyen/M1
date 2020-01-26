import { Component, OnInit } from '@angular/core';
import {FlashMessagesService} from 'angular2-flash-messages';

import { ProduitPanier } from '../models/produitPanier.model';
import { PanierService } from '../services/panier.service';
import { UserService } from '../services/user.service';
import { NgForm } from "@angular/forms";


@Component({
  selector: 'app-panier',
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.css']
})
export class PanierComponent implements OnInit {
  userPanier: ProduitPanier[] = new Array();;
  
  constructor(
    private userService: UserService,
    private panierService: PanierService,
    private flashMessage: FlashMessagesService,
    ) { }

  ngOnInit() {
    let id = JSON.parse(atob(this.userService.getToken().split('.')[1]))._id;
    this.panierService.getProduitPanier(id).subscribe(produitsPanier => {
      this.userPanier = produitsPanier; 
    });
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
        location.reload();
      },
      err => {
        this.flashMessage.show(err.error.join('<br/>'), { cssClass: 'alert-warning', timeout: 2000});
      }
    );
  }

  onSupprimer(id: String){
    this.panierService.supprimeProduitPanier(id).subscribe(
      res => {
        if(res.success){ 
          this.flashMessage.show('Vous avez supprimé un produit de votre panier !', { cssClass: 'alert-success', timeout: 2000});
          location.reload();
        }
      },
      err => {
        this.flashMessage.show(err.error.join('<br/>'), { cssClass: 'alert-warning', timeout: 2000});
      }
    );
  }

  onValidation(){
    let id = JSON.parse(atob(this.userService.getToken().split('.')[1]))._id;
    this.panierService.validerPanier(id).subscribe(
      res => {
        this.flashMessage.show('Votre panier a été validé!', { cssClass: 'alert-success', timeout: 2000});
        location.reload();
      },
      err => {
        this.flashMessage.show(err.error.join('<br/>'), { cssClass: 'alert-warning', timeout: 2000});
      }
    );
  }

  

}
