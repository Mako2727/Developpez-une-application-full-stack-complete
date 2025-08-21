import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Theme } from '../../shared/models/theme.interface';
import { MessageResponse } from '../../shared/models/messageResponse.interface';
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

getAllThemes(): Observable<Theme[]> {
  const token = localStorage.getItem('token');
  const url = `${this.pathService}api/topics`;
  
  console.log('URL complète:', url);
  console.log('Token envoyé:', token);
  
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics`, { headers });

}


subscribe(topicId: number): Observable<MessageResponse> {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

  const url = `${this.pathService}api/topics/${topicId}/subscribe`; // injecte l'id
  console.log('URL complète:', url);
  console.log('Token envoyé:', token);

  return this.httpClient.post<MessageResponse>(url,{}, { headers });

}

unsubscribe(topicId: number): Observable<string> {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

  const url = `${this.pathService}api/topics/${topicId}`; // injecte l'id
  console.log('URL complète:', url);
  console.log('Token envoyé:', token);

  return this.httpClient.delete<string>(url, { headers });

}


getSubscribedTheme(): Observable<Theme[]> {
  const token = localStorage.getItem('token');
  const url = `${this.pathService}api/topics`;
  
  console.log('URL complète:', url);
  console.log('Token envoyé:', token);
  
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics/subscribed`, { headers });

}
}