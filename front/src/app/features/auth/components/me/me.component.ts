import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from 'src/app/interfaces/user.interface';
import { UserUpdate } from '../../interfaces/userUpdate.interface';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {
  user: User | null = null;
  loading = true;
  error = false;
  password: string = ''; // Pour stocker le mot de passe modifié

  constructor(private router: Router,private authService: AuthService) {


  }
  email: string = '';
username: string = '';
successMessage: string | null = null;
errorMessage: string | null = null;

  ngOnInit(): void {
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

  loadUser(): void {
    this.authService.me().subscribe({
      next: (userData: User) => {
        this.user = userData;
        this.loading = false;
      },
      error: () => {
        this.error = true;
        this.loading = false;
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
    this.errorMessage = null;  // on efface le message d'erreur
    this.password = '';
    this.loadUser();
  },
  error: () => {
    this.errorMessage = 'Mise à jour KO';
    this.successMessage = null; // on efface le message de succès
  }
});
}

    goBack(): void {
    this.router.navigate(['dashboard']); 
  }
}