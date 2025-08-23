/// <reference types="jest" />

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CommentService } from './comment.service';
import { environment } from 'src/environments/environment';

describe('CommentService', () => {
  let service: CommentService;
  let httpMock: HttpTestingController;

  beforeAll(() => {
    // Remplacement complet de localStorage par un mock
    const store: { [key: string]: string } = {};
    Object.defineProperty(window, 'localStorage', {
      value: {
        getItem: (key: string) => store[key] || null,
        setItem: (key: string, value: string) => { store[key] = value; },
        removeItem: (key: string) => { delete store[key]; },
        clear: () => { Object.keys(store).forEach(k => delete store[k]); }
      },
      writable: true
    });

    // Ajout du token mock
    localStorage.setItem('token', 'mock-token');
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CommentService]
    });
    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should add a comment', () => {
    const articleId = 1;
    const mockComment = { content: 'Test comment' };
    const mockResponse = 'Comment added';

    service.addComment(articleId, mockComment).subscribe(res => {
      expect(res).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}api/posts/comments/${articleId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token'); // ✅ devrait passer
    expect(req.request.body).toEqual(mockComment);

    req.flush(mockResponse);
  });
});