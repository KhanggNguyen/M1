import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { RechercheService } from '../../services/recherche.service';
import { UserService } from '../../services/user.service';


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
    private userService : UserService
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

}
