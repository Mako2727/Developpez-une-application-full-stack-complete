/// <reference types="jest" />

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { CommentService } from './comment.service';
import { environment } from 'src/environments/environment';
import { createComment } from 'src/app/shared/models/createComment.interface';
import { HttpHeaders } from '@angular/common/http';

describe('CommentService', () => {
  let service: CommentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CommentService]
    });

    service = TestBed.inject(CommentService);
    httpMock = TestBed.inject(HttpTestingController);

  
    localStorage.setItem('token', 'mock-token');
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

it('should add a comment', () => {
  const articleId = 1;
  const mockComment: createComment = { content: 'Test comment' };
  const mockResponse = 'Comment added';

  service.addComment(articleId, mockComment).subscribe(res => {
    expect(res).toBe(mockResponse);
  });

  const req = httpMock.expectOne(`${environment.baseUrl}api/posts/comments/${articleId}`);
  expect(req.request.method).toBe('POST');

  expect(req.request.body).toEqual(mockComment);

  req.flush(mockResponse);
});
});