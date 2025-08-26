import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { User } from 'src/app/shared/models/user.interface';
import { UserUpdate } from '../../shared/models/userUpdate.interface';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ThemeService } from '../../core/services/theme.service';
import { Theme } from '../../shared/models/theme.interface';
import { MatCardModule } from '@angular/material/card';
import { SessionService } from 'src/app/core/services/session.service';
import { AuthSuccess } from 'src/app/shared/models/authSuccess.interface';
import { LoginRequest } from 'src/app/shared/models/loginRequest.interface';
import { SessionInformation } from 'src/app/shared/models/SessionInformation.interface';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [CommonModule, FormsModule, MatInputModule, MatButtonModule, MatCardModule],
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  user: User | null = null;
  loading = true;
  error = false;
  password: string = '';

  
  email: string = '';
  username: string = '';
  successMessage: string | null = null;
  errorMessage: string | null = null;
  subscribedThemes: Theme[] = [];
  


  themes: Theme[] = []; 

  constructor(
    private router: Router,
    private authService: AuthService,
    private themeService: ThemeService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.loadUser();
     this.loadSubscribedThemes();
  }


  loadUser(): void {
    this.authService.me().subscribe({
      next: (userData: User) => {
        this.user = userData;
        this.email = userData.email;
        this.username = userData.username;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  loadSubscribedThemes(): void {
    this.themeService.getSubscribedTheme().subscribe({
      next: (themes: Theme[]) => {
        this.subscribedThemes = themes;
      },
      error: (err) => {
        console.error('Erreur chargement des abonnements', err);
        this.subscribedThemes = [];
      }
    });
  }



updateProfile(): void {
  if (!this.user) return;

  const updatedUser: UserUpdate = {
    email: this.email,
    username: this.username,
    password: this.password || undefined,
  };


  this.authService.updateUser(updatedUser).subscribe({
    next: () => {

      
      const loginRequest: LoginRequest = {
        email: updatedUser.email,
        password: updatedUser.password || ''
      };

      this.authService.login(loginRequest).subscribe({
        next: (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);

          
          const sessionInfo: SessionInformation = {
            token: response.token,
            username: ''
          };
          this.sessionService.logIn(sessionInfo);

          
          this.authService.me().subscribe({
            next: (user: User) => {
              this.successMessage = 'Mise à jour OK';
              this.errorMessage = null;
              this.password = '';

              sessionInfo.username = user.username;
              this.sessionService.logIn(sessionInfo);

              
              this.loadUser();
            },
            error: (err) => {
              console.error('Erreur me() après relogin:', err);
              this.errorMessage = 'Erreur récupération utilisateur après mise à jour';
              this.successMessage = null;
            }
          });
        },
        error: (err) => {
          console.error('Erreur login après updateUser:', err);
          this.errorMessage = 'Impossible de relogin après mise à jour';
          this.successMessage = null;
        }
      });
    },
    error: () => {
      console.error('Erreur updateUser');
      this.errorMessage = 'Mise à jour KO';
      this.successMessage = null;
    }
  });
}
  goBack(): void {
    this.router.navigate(['dashboard']);
  }



 
toggleSubscription(theme: Theme): void {
  if (theme.subscribed) {
     
    this.themeService.subscribe(theme.id).subscribe({
      next: () => {
        this.loadSubscribedThemes();
      },
      error: (err) => {
        console.error('Erreur abonnement', err);
      }
    });
  } else {
  
    this.themeService.unsubscribe(theme.id).subscribe({
      next: () => {
        this.loadSubscribedThemes(); 
      },
      error: (err) => {
        console.error('Erreur désabonnement', err);
      }
    });
  }
}
}