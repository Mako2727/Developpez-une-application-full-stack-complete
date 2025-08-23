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

  constructor(private http: HttpClient) {}  // utilise http, pas HttpClient avec majuscule

  // Création d'article
  createArticle(article: NewArticle): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.pathService}api/posts`, article);
  }

  // Récupérer tous les articles
  getAllArticles(): Observable<postDetail[]> {
    return this.http.get<postDetail[]>(`${this.pathService}api/posts`);
  }

  // Récupérer un article par ID
  getArticleById(id: number): Observable<postDetail> {
    return this.http.get<postDetail>(`${this.pathService}api/posts/${id}`);
  }
}