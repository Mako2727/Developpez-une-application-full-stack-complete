import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { environment } from 'src/environments/environment';
import { NewArticle } from '../pages/interfaces/newarticle.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
      private pathService = environment.baseUrl;
  constructor(private HttpClient: HttpClient) {}

 createArticle(article: NewArticle): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.HttpClient.post(`${this.pathService}api/posts`, article, { headers });
  }

  getAllArticles(): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.HttpClient.get(`${this.pathService}api/posts`, { headers });
  }
}