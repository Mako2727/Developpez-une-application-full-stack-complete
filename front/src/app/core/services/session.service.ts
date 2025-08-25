import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { SessionInformation } from '../../shared/models/SessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {


  private isLoggedSubject = new BehaviorSubject<boolean>(false);
  public isLogged$: Observable<boolean> = this.isLoggedSubject.asObservable();


  private _sessionInformation: SessionInformation | null = null;

  constructor() {

    const stored = localStorage.getItem('sessionInformation');
    if (stored) {
      this._sessionInformation = JSON.parse(stored);
      this.isLoggedSubject.next(true);
    }
  }


  public get sessionInformation(): SessionInformation | null {
    return this._sessionInformation;
  }

  public get isLogged(): boolean {
    return !!this._sessionInformation;
  }

  
  public logIn(sessionInfo: SessionInformation): void {
    this._sessionInformation = sessionInfo;
    this.isLoggedSubject.next(true);
    localStorage.setItem('sessionInformation', JSON.stringify(sessionInfo)); 
  }


  public logOut(): void {
    this._sessionInformation = null;
    this.isLoggedSubject.next(false);
    localStorage.removeItem('sessionInformation'); 
  }
}