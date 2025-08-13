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
export class PostService {

}