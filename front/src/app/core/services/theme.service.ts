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
  
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics`);

}

getAllThemes(): Observable<Theme[]> {
  const token = localStorage.getItem('token');
  const url = `${this.pathService}api/topics`;
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics`);

}


subscribe(topicId: number): Observable<MessageResponse> {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

  const url = `${this.pathService}api/topics/${topicId}/subscribe`; 

  return this.httpClient.post<MessageResponse>(url,{});

}

unsubscribe(topicId: number): Observable<string> {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });

  const url = `${this.pathService}api/topics/${topicId}`; 
  return this.httpClient.delete<string>(url);

}


getSubscribedTheme(): Observable<Theme[]> {
  const token = localStorage.getItem('token');
  const url = `${this.pathService}api/topics`;
  
  const headers = new HttpHeaders({'Authorization': `Bearer ${token}`  });
return this.httpClient.get<Theme[]>(`${this.pathService}api/topics/subscribed`);

}
}