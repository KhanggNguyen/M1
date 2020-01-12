import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FlashMessagesService } from 'angular2-flash-messages';
import { NgForm } from "@angular/forms";

import { ProduitsService } from '../services/produits.service';
import { UserService } from '../services/user.service';
import { PanierService } from '../services/panier.service';

@Component({
  selector: 'app-produits',
  templateUrl: './produits.component.html',
  styleUrls: ['./produits.component.css']
})
export class ProduitsComponent implements OnInit {
  private produits : Object[] = new Array();
  private categories : Object[] = new Array();
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private produitsService: ProduitsService,
    private userService: UserService,
    private panierService: PanierService,
    private flashMessage: FlashMessagesService,
  ) { }

  ngOnInit() {
    this.route.params.subscribe(( params: Params) => {
      if (params["categorie"] !== undefined){
        this.produitsService.getProduitsParCategorie(params["categorie"]).subscribe(produits => { 
          this.produits = produits;
          
        });
      }else if(this.router.url === '/categories'){
        this.produitsService.getCategories().subscribe(categories => {
          this.categories = categories;
        });
      }else{
        this.produitsService.getProduits().subscribe(produits => {
          this.produits = produits;
        });
      }
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
      }
    );
  }

  isAuthenticated(){
    return this.userService.isLoggedIn();
  }
}
