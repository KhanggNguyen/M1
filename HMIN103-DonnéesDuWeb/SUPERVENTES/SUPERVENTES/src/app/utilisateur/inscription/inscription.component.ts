import { Component, OnInit } from '@angular/core';
import { UserService } from '../../shared/user.service';
import { Router} from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css'],
})
export class InscriptionComponent implements OnInit {
  private user : Observable<string>;
  registered = false;
	submitted = false;
  userForm: FormGroup;
  serverErrorMessages: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) { }

  invalidFirstName()
  {
  	return (this.submitted && this.userForm.controls._prenom.errors != null);
  }

  invalidLastName()
  {
  	return (this.submitted && this.userForm.controls._nom.errors != null);
  }

  invalidEmail()
  {
  	return (this.submitted && this.userForm.controls._email.errors != null);
  }

  invalidPassword()
  {
  	return (this.submitted && this.userForm.controls._password.errors != null);
  }

  ngOnInit() {
    if(this.userService.isLoggedIn()){
      this.router.navigateByUrl('/produits');
    }
    this.userForm = this.formBuilder.group({
  		_prenom: ['', Validators.required],
  		_nom: ['', Validators.required],
  		_email: ['', [Validators.required, Validators.email]],
  		_password: ['', [Validators.required, Validators.minLength(5)]],
  	});
  }

  onSubmit()
  {
  	this.submitted = true;

  	if(this.userForm.invalid == true)
  		return;
  	else
  	{ 
      this.userService.postUser(this.userForm.value).subscribe(
        res => {
          this.registered = true;
          setTimeout(() => this.registered = false, 400);//enlever le message après 4s
          this.userForm.reset();
          this.router.navigate(['/produits']);
        },
        err => { 
          if(err.status === 422)
            this.serverErrorMessages = err.error.join('<br/>');
          else
            this.serverErrorMessages = "Veuillez contacter l'administrateur.";
        }
      )
  	}
  }
}
