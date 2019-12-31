import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import {FlashMessage} from 'angular-flash-message';

import { ProduitPanier } from '../models/produitPanier.model';
import { PanierService } from '../services/panier.service';
import { UserService } from '../services/user.service';
import { ProduitsService } from '../services/produits.service';

@Component({
  selector: 'app-panier',
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.css']
})
export class PanierComponent implements OnInit {
  userPanier: ProduitPanier[];
  serverMessages: String;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private produitsService: ProduitsService,
    private userService: UserService,
    private panierService: PanierService,
    private flashMessage: FlashMessage
    ) { }

  ngOnInit() {
    let id = JSON.parse(atob(this.userService.getToken().split('.')[1]))._id;
    this.panierService.getProduitPanier(id).subscribe(produitsPanier => {
      this.userPanier = produitsPanier; 
    });
  }

  onSupprimer(id: String){
    this.panierService.supprimeProduitPanier(id).subscribe(
      res => {
        if(res.success){
          this.serverMessages = 'Vous avez supprimé un produit de votre panier !';
        }
        this.router.navigateByUrl('/produits');
      },
      err => {
      }
    );
  }

  

}
