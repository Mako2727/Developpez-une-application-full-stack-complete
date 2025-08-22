import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { LoginRequest } from '../../shared/models/loginRequest.interface'; 
import { AuthService } from '../../core/services/auth.service';
import { SessionService } from 'src/app/core/services/session.service';
import { AuthSuccess } from '../../shared/models/authSuccess.interface';
import { User } from 'src/app/shared/models/user.interface';

@Component({
  selector: 'app-login',
  standalone: true, // <-- rendu standalone
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  registerForm!: FormGroup;
  public onError = false;

  constructor(
    private authService: AuthService, 
    private fb: FormBuilder, 
    private router: Router,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    console.log('LoginComponent loaded');
    this.registerForm = this.fb.group({    
      email: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      console.log("Submit login");
      const loginRequest: LoginRequest = this.registerForm.value; 
      this.authService.login(loginRequest).subscribe(
        (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);
          console.log("Token reçu", response.token);
          this.authService.me().subscribe((user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/article']);
          }); 
        },
        error => this.onError = true
      );
    }
  }

  goBack(): void {
    this.router.navigate(['/']);
  }
}