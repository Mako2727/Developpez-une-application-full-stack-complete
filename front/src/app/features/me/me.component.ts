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
    private themeService: ThemeService
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
        console.log('Abonnements récupérés:', themes); 
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
      password: this.password ? this.password : undefined,
    };

    this.authService.updateUser(updatedUser).subscribe({
      next: () => {
        this.successMessage = 'Mise à jour OK';
        this.errorMessage = null;
        this.password = '';
        this.loadUser();
      },
      error: () => {
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
        console.log(`Abonné au thème "${theme.name}"`);
        this.loadSubscribedThemes();
      },
      error: (err) => {
        console.error('Erreur abonnement', err);
      }
    });
  } else {
  
    this.themeService.unsubscribe(theme.id).subscribe({
      next: () => {
        console.log(`Désabonné du thème "${theme.name}"`);
        this.loadSubscribedThemes(); 
      },
      error: (err) => {
        console.error('Erreur désabonnement', err);
      }
    });
  }
}
}