import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../core/services/session.service';
import { User } from 'src/app/shared/models/user.interface';
import { Router } from '@angular/router';
import { SessionInformation } from 'src/app/shared/models/SessionInformation.interface';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user: SessionInformation | null = null; // on utilise sessionInformation
  loading = true;

  constructor(private sessionService: SessionService, private router: Router) { }

  ngOnInit(): void {
    // récupérer l'utilisateur depuis sessionInformation
    this.user = this.sessionService.sessionInformation ?? null;
    this.loading = false;
  }

  goToProfile(): void {
    this.router.navigate(['/me']);  // ou la route que tu veux
  }

  logOut(): void {
    this.user = null;
    this.sessionService.logOut();       // vide la session correctement
    localStorage.removeItem('token');   // supprime le token localement
    this.router.navigate(['']); 
  }
}