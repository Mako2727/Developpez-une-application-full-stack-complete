import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
//import { SessionInformation } from '../../shared/models/sessionInformation.interface';
import { SessionInformation } from '../../shared/models/SessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  // Indique si l'utilisateur est connecté
  private isLoggedSubject = new BehaviorSubject<boolean>(false);
  public isLogged$: Observable<boolean> = this.isLoggedSubject.asObservable();

  // Contient les infos de session (token, user, etc.)
  private _sessionInformation: SessionInformation | null = null;

  constructor() {
    // Optionnel : charger la session depuis le localStorage si persistance souhaitée
    const stored = localStorage.getItem('sessionInformation');
    if (stored) {
      this._sessionInformation = JSON.parse(stored);
      this.isLoggedSubject.next(true);
    }
  }

  // Getter pratique pour accéder aux infos de session
  public get sessionInformation(): SessionInformation | null {
    return this._sessionInformation;
  }

  public get isLogged(): boolean {
    return !!this._sessionInformation;
  }

  // Méthode pour connecter l'utilisateur
  public logIn(sessionInfo: SessionInformation): void {
    this._sessionInformation = sessionInfo;
    this.isLoggedSubject.next(true);
    localStorage.setItem('sessionInformation', JSON.stringify(sessionInfo)); // persistance optionnelle
  }

  // Méthode pour déconnecter l'utilisateur
  public logOut(): void {
    this._sessionInformation = null;
    this.isLoggedSubject.next(false);
    localStorage.removeItem('sessionInformation'); // effacer le token
  }
}