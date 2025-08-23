import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { NewArticle } from '../../shared/models/newarticle.interface';
import { postDetail } from '../../shared/models/postDetail.interface';
import { MessageResponse } from '../../shared/models/messageResponse.interface';
import { createComment } from '../../shared/models/createComment.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
      private pathService = environment.baseUrl;
  constructor(private HttpClient: HttpClient) {}


addComment(articleId: number, comment: createComment): Observable<string> {
  return this.HttpClient.post<string>(`${this.pathService}api/posts/comments/${articleId}`,comment);
}

}

