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
  user: SessionInformation | null = null; 
  loading = true;

  constructor(private sessionService: SessionService, private router: Router) { }

  ngOnInit(): void {
   
    this.user = this.sessionService.sessionInformation ?? null;
    this.loading = false;
  }

  goToProfile(): void {
    this.router.navigate(['/me']);  
  }

  logOut(): void {
    this.user = null;
    this.sessionService.logOut();       
    localStorage.removeItem('token');  
    this.router.navigate(['']); 
  }
}