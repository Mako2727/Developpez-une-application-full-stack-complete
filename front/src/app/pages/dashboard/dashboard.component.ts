import { Component, OnInit } from '@angular/core';
import { SessionService } from '../../services/session.service';
import { User } from 'src/app/interfaces/user.interface';
import { Router } from '@angular/router';




@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  user: User | null | undefined = null;
  loading = true;

    constructor(private sessionService: SessionService,private router: Router) { }

  ngOnInit(): void {
     this.user = this.sessionService.user;
    this.loading = false;
  }

    goToProfile(): void {
    this.router.navigate(['/me']);  // ou la route que tu veux
  }

  logOut(): void {
       this.user = null;
    localStorage.removeItem('token'); // supprime le token localement
    this.router.navigate(['']); 

  }

 

}
