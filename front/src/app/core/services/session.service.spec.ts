/// <reference types="jest" />

import { SessionService } from './session.service';
import { SessionInformation } from '../../shared/models/SessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;
  const mockSession: SessionInformation = { 
    token: 'mock-token', 
    username: 'testuser', 
    id: 1, 
    firstName: 'Test', 
    lastName: 'User', 
    admin: false 
  };

  beforeEach(() => {
    localStorage.clear();
    service = new SessionService();
  });

  it('should initialize with no session', (done) => {
    expect(service.sessionInformation).toBeNull();
    service.isLogged$.subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });
  });

  it('should log in a user', (done) => {
    service.logIn(mockSession);

    expect(service.sessionInformation).toEqual(mockSession);

    service.isLogged$.subscribe(isLogged => {
      expect(isLogged).toBe(true);
      done();
    });

    const stored = JSON.parse(localStorage.getItem('sessionInformation')!);
    expect(stored).toEqual(mockSession);
  });

  it('should log out a user', (done) => {
    service.logIn(mockSession); // login d’abord
    service.logOut();            // puis logout

    expect(service.sessionInformation).toBeNull();

    service.isLogged$.subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });

    expect(localStorage.getItem('sessionInformation')).toBeNull();
  });
});