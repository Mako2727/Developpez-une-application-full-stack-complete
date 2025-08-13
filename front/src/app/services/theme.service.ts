import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../pages/interfaces/theme.interface';
@Injectable({
  providedIn: 'root'
})
export class ThemeService { 

    private pathService = environment.baseUrl;
  constructor(private  httpClient: HttpClient) {}

getThemes(): Observable<Theme[]> {
  const token = localStorage.getItem('token');
  const url = `${this.pathService}api/topics`;
  
  console.log('URL complète:', url);
  console.log('Token envoyé:', token);
  
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics`, { headers });

}
}