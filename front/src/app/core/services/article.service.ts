import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { NewArticle } from '../../shared/models/newarticle.interface';
import { postDetail } from '../../shared/models/postDetail.interface';
import { MessageResponse } from '../../shared/models/messageResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
      private pathService = environment.baseUrl;
  constructor(private HttpClient: HttpClient) {}

 createArticle(article: NewArticle): Observable<MessageResponse> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.HttpClient.post<MessageResponse>(`${this.pathService}api/posts`, article, { headers });
  }

  getAllArticles(): Observable<postDetail[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.HttpClient.get<postDetail[]>(`${this.pathService}api/posts`, { headers });
  }

getArticleById(id: number): Observable<postDetail> {
  const token = localStorage.getItem('token');
  const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
  // utilise l'interpolation pour passer l'id dans l'URL
  return this.HttpClient.get<postDetail>(`${this.pathService}api/posts/${id}`, { headers });
}
}