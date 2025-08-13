import { Component } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
    user: User | null | undefined = null;
  title = 'front';
    constructor(private router: Router) {}

    logOut(): void {
       this.user = null;
    localStorage.removeItem('token'); // supprime le token localement
    this.router.navigate(['']); 

  }
}



