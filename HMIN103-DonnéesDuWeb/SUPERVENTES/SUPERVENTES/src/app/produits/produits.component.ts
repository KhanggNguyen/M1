import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { ProduitsService } from '../shared/produits.service';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-produits',
  templateUrl: './produits.component.html',
  styleUrls: ['./produits.component.css']
})
export class ProduitsComponent implements OnInit {
  private produits : Object[] = new Array();

  constructor(
    private route: ActivatedRoute,
    private produitsService: ProduitsService,
    private userService: UserService
    ) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      if (params["categorie"] !== undefined){
        console.log("/produits/"+params['categorie']);
        this.produitsService.getProduitsParCategorie(params["categorie"]).subscribe(produits => { 
          this.produits = produits;
        });
      }else{
        this.produitsService.getProduits().subscribe(produits => {
          this.produits = produits;
        });
      }
    });
  }

  isAuthenticated(){
    return this.userService.isLoggedIn();
  }
}
