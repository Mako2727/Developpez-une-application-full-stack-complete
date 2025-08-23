/// <reference types="jest" />

import { JwtInterceptor } from './jwt.interceptor';
import { HttpRequest, HttpHandler } from '@angular/common/http';

describe('JwtInterceptor (Jest pur)', () => {
  let interceptor: JwtInterceptor;
  let httpHandler: HttpHandler;

  beforeEach(() => {
    interceptor = new JwtInterceptor();
    httpHandler = {
      handle: jest.fn().mockImplementation(req => req) // retourne la requête pour inspection
    } as unknown as HttpHandler;
  });

  afterEach(() => {
    jest.restoreAllMocks();
  });

  it('should add Authorization header when token exists', () => {
    // Mock localStorage
    Object.defineProperty(window, 'localStorage', {
      value: {
        getItem: jest.fn().mockReturnValue('mock-token')
      },
      writable: true,
    });

    const req = new HttpRequest('GET', '/api/test');
    interceptor.intercept(req, httpHandler);

    const handledReq = (httpHandler.handle as jest.Mock).mock.calls[0][0] as HttpRequest<any>;
    expect(handledReq.headers.get('Authorization')).toBe('Bearer mock-token');
  });

  it('should not add Authorization header when token is absent', () => {
    Object.defineProperty(window, 'localStorage', {
      value: {
        getItem: jest.fn().mockReturnValue(null)
      },
      writable: true,
    });

    const req = new HttpRequest('GET', '/api/test');
    interceptor.intercept(req, httpHandler);

    const handledReq = (httpHandler.handle as jest.Mock).mock.calls[0][0] as HttpRequest<any>;
    expect(handledReq.headers.get('Authorization')).toBeNull();
  });
});