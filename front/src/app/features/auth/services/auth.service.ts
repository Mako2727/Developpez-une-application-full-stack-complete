import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { AuthSuccess  } from '../interfaces/authSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/interfaces/user.interface';
import { environment } from 'src/environments/environment';
import { userMe } from 'src/app/interfaces/userMe.interface';
import { UserUpdate } from '../interfaces/userUpdate.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = environment.baseUrl;

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    const url = `${this.pathService}api/auth/register`;
console.log('URL complète absolue XX:', url);
console.log("JSON envoyé :", JSON.stringify(registerRequest));
    return this.httpClient.post<AuthSuccess>(`${this.pathService}api/auth/register`, registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
       const url = `${this.pathService}api/auth/login`;
console.log('URL complète absolue XX:', url);
    console.log("JSON envoyé :", JSON.stringify(loginRequest));
    return this.httpClient.post<AuthSuccess>(`${this.pathService}api/auth/login`, loginRequest);
  }

  public me(): Observable<userMe> {
    const token = localStorage.getItem('token');
console.log('Token envoyé:', token);
 const headers = new HttpHeaders({'Authorization': `Bearer ${token}`});
    return this.httpClient.get<userMe>(`${this.pathService}api/auth/me`, { headers });
  }


public updateUser(updatedUser: UserUpdate): Observable<userMe> {
  const token = localStorage.getItem('token');
  console.log('Token envoyé:', token);
  console.log('Données UserUpdate à envoyer:', updatedUser); 
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}` });
  return this.httpClient.put<userMe>(`${this.pathService}api/auth/me`,updatedUser,  { headers }    );
}
}
