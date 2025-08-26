import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { SessionService } from "../services/session.service";

@Injectable({ providedIn: "root" })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private sessionService: SessionService
  ) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    
    if (!token) {
      this.router.navigate(['']); 
      return false;
    }

    
    if (this.sessionService.isLogged) {
      return true;
    }

   
    return true;
  }
}