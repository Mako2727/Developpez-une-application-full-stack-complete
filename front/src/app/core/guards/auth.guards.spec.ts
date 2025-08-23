/// <reference types="jest" />
import { AuthGuard } from './auth.guards';
import { Router } from '@angular/router';
import { SessionService } from '../services/session.service';
import { fakeAsync, TestBed, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let routerMock: Partial<Router>;
  let sessionServiceMock: Partial<SessionService>;

  beforeEach(() => {
    routerMock = {
      navigate: jest.fn()
    };
    sessionServiceMock = {
      isLogged: false
    };

    guard = new AuthGuard(routerMock as Router, sessionServiceMock as SessionService);
    localStorage.clear(); // pour être sûr que le token n'existe pas
  });

  it('should redirect to home if no token', () => {
    const result = guard.canActivate();
    expect(result).toBe(false);
    expect(routerMock.navigate).toHaveBeenCalledWith(['']);
  });

  it('should allow activation if token exists and session not logged', () => {
    localStorage.setItem('token', 'fake-token');
    const result = guard.canActivate();
    expect(result).toBe(true);
    expect(routerMock.navigate).not.toHaveBeenCalled();
  });

  it('should allow activation if token exists and session is logged', () => {
    localStorage.setItem('token', 'fake-token');
    sessionServiceMock.isLogged = true;
    const result = guard.canActivate();
    expect(result).toBe(true);
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

    localStorage.clear(); // s'assurer qu'il n'y a pas de token résiduel
    sessionService.isLogged = false; // état initial
  });

  it('should allow access when token exists and session is logged', fakeAsync(() => {
    localStorage.setItem('token', 'mock-token');
    sessionService.isLogged = true;

    const result = guard.canActivate();
    tick();
    expect(result).toBe(true);
  }));

  it('should redirect to home when token is missing', fakeAsync(() => {
    const navigateSpy = jest.spyOn(router, 'navigate');

    const result = guard.canActivate();
    tick();

    expect(result).toBe(false);
    expect(navigateSpy).toHaveBeenCalledWith(['']);
  }));

  it('should allow access when token exists but session not logged', fakeAsync(() => {
    localStorage.setItem('token', 'mock-token');
    sessionService.isLogged = false;

    const result = guard.canActivate();
    tick();

    expect(result).toBe(true);
  }));
});