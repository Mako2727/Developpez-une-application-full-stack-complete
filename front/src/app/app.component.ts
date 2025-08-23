import { Component, HostListener, ChangeDetectorRef } from '@angular/core';
import { RouterModule, Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';

// Angular Material
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

import { SessionService } from './core/services/session.service';
import { filter } from 'rxjs';
import { NgZone } from '@angular/core'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  menuOpen = false;
  currentUrl: string = '';
 

constructor(
  public sessionService: SessionService,
  private router: Router,
  private cdr: ChangeDetectorRef
) {
  this.currentUrl = this.router.url;

  // <-- ici, c'est le subscribe qui écoute la navigation
  this.router.events
    .pipe(filter(event => event instanceof NavigationEnd))
    .subscribe((event) => {
      const navEnd = event as NavigationEnd;
      this.currentUrl = navEnd.urlAfterRedirects;
      console.log('NavigationEnd:', this.currentUrl);
      this.cdr.detectChanges(); // force la mise à jour du template
    });
}
  logOut(): void {
    this.sessionService.logOut();
    this.menuOpen = false; // fermer menu burger si ouvert
    this.router.navigate(['']);     
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    if (globalThis.window.innerWidth > 768) {
      this.menuOpen = false; // ferme menu burger sur grand écran
    }
  }

  // Getter pratique pour responsive
  get isMobile(): boolean {
    return globalThis.window.innerWidth <= 768;
  }

get showLogo(): boolean {
  const authPages = ['/login', '/register'];
  return this.sessionService.isLogged || authPages.includes(this.currentUrl);
}
}