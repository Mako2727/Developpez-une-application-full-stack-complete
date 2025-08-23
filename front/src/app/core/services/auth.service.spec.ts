/// <reference types="jest" />


import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from 'src/environments/environment';
import { RegisterRequest } from '../../shared/models/registerRequest.interface';
import { AuthSuccess } from '../../shared/models/authSuccess.interface';
import { LoginRequest } from '../../shared/models/loginRequest.interface';
import { userMe } from 'src/app/shared/models/userMe.interface';
import { UserUpdate } from '../../shared/models/userUpdate.interface';
import { RouterTestingModule } from '@angular/router/testing';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthGuard } from '../guards/auth.guards';
import { SessionService } from './session.service';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './jwt.interceptor';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

beforeEach(() => {
  TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [
      AuthService,
      { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
    ]
  });

  service = TestBed.inject(AuthService);
  httpMock = TestBed.inject(HttpTestingController);

  // token pour l'intercepteur
  const sessionService = TestBed.inject(SessionService);
  sessionService.logIn({ token: 'mock-token', username: 'mock-user' });
});

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should register a user', () => {
    const mockRequest: RegisterRequest = { name: 'Test', email: 'test@test.com', password: '123456' };
    const mockResponse: AuthSuccess = { token: 'mock-token' };

    service.register(mockRequest).subscribe(res => expect(res).toEqual(mockResponse));

    const req = httpMock.expectOne(`${environment.baseUrl}api/auth/register`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should login a user', () => {
    const mockRequest: LoginRequest = { email: 'test@test.com', password: '123456' };
    const mockResponse: AuthSuccess = { token: 'mock-token' };

    service.login(mockRequest).subscribe(res => expect(res).toEqual(mockResponse));

    const req = httpMock.expectOne(`${environment.baseUrl}api/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should get user info via me()', () => {
    const mockUser: userMe = { email: 'test@test.com', username: 'TestUser', subscriptions: [] };

    service.me().subscribe(res => expect(res).toEqual(mockUser));

    const req = httpMock.expectOne(`${environment.baseUrl}api/auth/me`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');
    req.flush(mockUser);
  });

  it('should update user info via updateUser()', () => {
    const updatedUser: UserUpdate = { email: 'new@test.com', username: 'NewUser' };
    const mockUser: userMe = { email: 'test@test.com', username: 'TestUser', subscriptions: [] };

    service.updateUser(updatedUser).subscribe(res => expect(res).toEqual(mockUser));

    const req = httpMock.expectOne(`${environment.baseUrl}api/auth/me`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');
    expect(req.request.body).toEqual(updatedUser);
    req.flush(mockUser);
  });
});


describe('AuthGuard Integration with fakeAsync', () => {
  let guard: AuthGuard;
  let router: Router;
  let sessionService: SessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([])],
      providers: [AuthGuard, SessionService]
    });

    guard = TestBed.inject(AuthGuard);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);

    localStorage.clear();
    sessionService.logOut(); // état initial déconnecté
  });

  it('should allow access when token exists and session is logged', fakeAsync(() => {
    localStorage.setItem('token', 'mock-token');
    sessionService.logIn({ token: 'mock-token', username: 'mock-user' });

    const canActivate = guard.canActivate();
    tick();
    expect(canActivate).toBe(true);
  }));

  it('should redirect to home when token is missing', fakeAsync(() => {
    const spy = jest.spyOn(router, 'navigate');

    const canActivate = guard.canActivate();
    tick();
    expect(canActivate).toBe(false);
    expect(spy).toHaveBeenCalledWith(['']);
  }));

  it('should allow access when token exists but session not logged', fakeAsync(() => {
    localStorage.setItem('token', 'mock-token');
    sessionService.logOut(); // session inactive

    const canActivate = guard.canActivate();
    tick();
    expect(canActivate).toBe(true);
  }));
});