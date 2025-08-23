/// <reference types="jest" />

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ArticleService } from './article.service';
import { NewArticle } from '../../shared/models/newarticle.interface';
import { postDetail } from '../../shared/models/postDetail.interface';
import { MessageResponse } from '../../shared/models/messageResponse.interface';
import { SessionService } from './session.service';
import { JwtInterceptor } from './jwt.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

describe('ArticleService', () => {
  let service: ArticleService;
  let httpMock: HttpTestingController;
  let mockSessionService: Partial<SessionService>;

beforeEach(() => {
  TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [
      ArticleService,
      { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ]
  });

  service = TestBed.inject(ArticleService);
  httpMock = TestBed.inject(HttpTestingController);

  // On simule le token dans le SessionService ou localStorage selon ton interceptor
  localStorage.setItem('sessionInformation', JSON.stringify({ token: 'fake-token', username: 'test' }));
});

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should create an article', () => {
    const newArticle: NewArticle = {
      title: 'Test Article',
      content: 'Contenu test',
      topicId: 1
    };
    const mockResponse: MessageResponse = { message: 'Article created' };

    service.createArticle(newArticle).subscribe(res => {
      expect(res).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${service['pathService']}api/posts`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Authorization')).toBe('Bearer fake-token');

    req.flush(mockResponse);
  });

  it('should get all articles', () => {
    const mockArticles: postDetail[] = [
      { id: 1, title: 'Article 1', content: 'Contenu 1', authorName: 'Auteur 1', topicName: 'Topic 1', createdAt: '2025-08-23', comments: [] },
      { id: 2, title: 'Article 2', content: 'Contenu 2', authorName: 'Auteur 2', topicName: 'Topic 2', createdAt: '2025-08-23', comments: [] }
    ];

    service.getAllArticles().subscribe(res => {
      expect(res).toEqual(mockArticles);
    });

    const req = httpMock.expectOne(`${service['pathService']}api/posts`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer fake-token');

    req.flush(mockArticles);
  });

  it('should get article by id', () => {
    const mockArticle: postDetail = {
      id: 1,
      title: 'Article 1',
      content: 'Contenu 1',
      authorName: 'Auteur 1',
      topicName: 'Topic 1',
      createdAt: '2025-08-23',
      comments: []
    };

    service.getArticleById(1).subscribe(res => {
      expect(res).toEqual(mockArticle);
    });

    const req = httpMock.expectOne(`${service['pathService']}api/posts/1`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer fake-token');

    req.flush(mockArticle);
  });
});