import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from '../../shared/models/loginRequest.interface';
import { AuthSuccess  } from '../../shared/models/authSuccess.interface';
import { RegisterRequest } from '../../shared/models/registerRequest.interface';
import { User } from 'src/app/shared/models/user.interface';
import { environment } from 'src/environments/environment';
import { userMe } from 'src/app/shared/models/userMe.interface';
import { UserUpdate } from '../../shared/models/userUpdate.interface';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = environment.baseUrl;

  constructor(private httpClient: HttpClient) { }

 
  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    const url = `${this.pathService}api/auth/register`;
    return this.httpClient.post<AuthSuccess>(url, registerRequest);
  }

 
  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    const url = `${this.pathService}api/auth/login`;
    return this.httpClient.post<AuthSuccess>(url, loginRequest);
  }

public me(): Observable<userMe> {
  return this.httpClient.get<userMe>(`${this.pathService}api/auth/me`);
}


  public updateUser(updatedUser: UserUpdate): Observable<userMe> {
    return this.httpClient.put<userMe>(`${this.pathService}api/auth/me`, updatedUser);
  }
}