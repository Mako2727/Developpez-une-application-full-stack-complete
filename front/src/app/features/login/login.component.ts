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
import { SessionInformation } from '../../shared/models/SessionInformation.interface';

@Component({
  selector: 'app-login',
  standalone: true,
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
  if (!this.registerForm.valid) return;

  console.log("Submit login");
  const loginRequest: LoginRequest = this.registerForm.value; 

  this.authService.login(loginRequest).subscribe({
    next: (response: AuthSuccess) => {
      console.log("Token reçu", response.token);

      // Stocker le token dans localStorage
      localStorage.setItem('token', response.token);

      // Créer une session minimaliste pour que l'interceptor fonctionne immédiatement
      const sessionInfo: SessionInformation = {
        token: response.token,
        username: '' // temporaire, sera mis à jour
      };
      this.sessionService.logIn(sessionInfo);

      // Récupérer les infos utilisateur
      this.authService.me().subscribe({
        next: (user: User) => {
          console.log('Infos utilisateur récupérées :', user);

          // Mettre à jour sessionInfo avec les infos réelles
          sessionInfo.username = user.username;
          sessionInfo.id = undefined; // si besoin ou user.id si disponible
          this.sessionService.logIn(sessionInfo); // mise à jour complète

          // Navigation après avoir tout mis à jour
          this.router.navigate(['/article']);
        },
        error: (err) => {
          console.error('Erreur récupération utilisateur me():', err);
          this.onError = true;
          // On pourrait rediriger ou déconnecter l’utilisateur si me() échoue
        }
      });
    },
    error: (err) => {
      console.error('Erreur login:', err);
      this.onError = true;
    }
  });
}

  goBack(): void {
    this.router.navigate(['/']);
  }
}