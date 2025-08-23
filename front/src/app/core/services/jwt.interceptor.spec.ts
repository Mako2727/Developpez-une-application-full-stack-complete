/// <reference types="jest" />

import { JwtInterceptor } from './jwt.interceptor';
import { HttpRequest, HttpHandler, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { SessionService } from './session.service';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('JwtInterceptor', () => {
  let interceptor: JwtInterceptor;
  let httpMock: HttpTestingController;
  let http: HttpClient;

  let sessionServiceMock: Partial<SessionService>;

  beforeEach(() => {
    // On crée un mock pour sessionService avec un getter sur sessionInformation
    sessionServiceMock = {};
    Object.defineProperty(sessionServiceMock, 'sessionInformation', {
      get: () => ({ token: 'mock-token', username: 'testuser' })
    });

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        {
          provide: HTTP_INTERCEPTORS,
          useClass: JwtInterceptor,
          multi: true
        }
      ]
    });

    interceptor = TestBed.inject(JwtInterceptor);
    httpMock = TestBed.inject(HttpTestingController);
    http = TestBed.inject(HttpClient);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should add Authorization header', () => {
    http.get('/test').subscribe();

    const req = httpMock.expectOne('/test');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');
    req.flush({});
  });
});