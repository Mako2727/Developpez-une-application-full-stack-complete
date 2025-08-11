import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators ,FormGroup} from '@angular/forms';
import { LoginRequest } from '../../interfaces/loginRequest.interface'; 
import { AuthService } from '../../services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { User } from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  registerForm!: FormGroup;
    public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });
    public onError = false;

  constructor(private authService: AuthService, 
    private fb: FormBuilder, 
    private router: Router,
    private sessionService: SessionService) { }

  ngOnInit(): void {
     console.log('RegisterComponent loaded');
    this.registerForm = this.fb.group({    
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

     onSubmit(): void {
        // if (this.registerForm.valid) {
         console.log("Submit login");
     const loginRequest =  this.registerForm.value; 
    this.authService.login(loginRequest).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        console.log("Token recu ", response.token )
       /*this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/rentals'])
        });*/
        //this.router.navigate(['/rentals'])
      },
      error => this.onError = true
    );
      //}
  }

  goBack(){
        this.router.navigate(['/']);
  }

}
