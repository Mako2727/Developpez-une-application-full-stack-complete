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

  constructor(private http: HttpClient) {}  


  createArticle(article: NewArticle): Observable<MessageResponse> {
    return this.http.post<MessageResponse>(`${this.pathService}api/posts`, article);
  }


  getAllArticles(): Observable<postDetail[]> {
    return this.http.get<postDetail[]>(`${this.pathService}api/feed`);
  }


  getArticleById(id: number): Observable<postDetail> {
    return this.http.get<postDetail>(`${this.pathService}api/posts/${id}`);
  }
}