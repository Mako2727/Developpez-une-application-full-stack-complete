import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from 'src/app/interfaces/user.interface';

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

  constructor(private authService: AuthService) {


  }
  email: string = '';
username: string = '';

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

    // Prépare les données à envoyer, avec mot de passe uniquement s'il est modifié
    const updatedUser = {
      email: this.user.email,
      username: this.user.username,
      password: this.password ? this.password : undefined,
    };

   /* this.authService.updateUser(updatedUser).subscribe({
      next: () => {
        alert('Profil mis à jour avec succès !');
        this.password = ''; // reset champ password
        this.loadUser(); // recharge les données à jour
      },
      error: () => {
        alert('Erreur lors de la mise à jour du profil.');
      }
    });*/
  }
}