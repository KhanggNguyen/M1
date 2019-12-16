import { Component, OnInit } from '@angular/core';
import { Location } from "@angular/common";
import { Router } from "@angular/router";
import { UserService } from '../shared/user.service';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  route: string;

  constructor(private userService: UserService, location: Location, private router: Router) { 
    router.events.subscribe(val => {
      if (location.path() != "") {
        this.route = location.path();
      } else {
        this.route = "Produits";
      }
    });
  }

  ngOnInit() {
  }

  onLogout(){
    this.userService.deleteToken();
    this.userService.deleteAuthenticated();
    this.router.navigate(['/produits']);
  }
  
  isAuthenticated(){
    return this.userService.isLoggedIn();
  }

}
