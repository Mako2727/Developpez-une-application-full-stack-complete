/// <reference types="jest" />

import { TestBed } from '@angular/core/testing';
import { SessionService } from './session.service';
import { User } from '../../shared/models/user.interface';

describe('SessionService', () => {
  let service: SessionService;
  const mockUser = { email: 'test@test.com', username: 'testuser' };

  beforeEach(() => {
    service = new SessionService();
    localStorage.clear();
  });

  it('should log in a user', () => {
    service.logIn(mockUser);

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(true);
      expect(service.user).toEqual(mockUser);
    });
  });

  it('should log out a user', () => {
    service.logIn(mockUser); // d'abord login

    service.logOut(); // puis logout

    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      expect(service.user).toBeUndefined();
      expect(localStorage.getItem('token')).toBeNull();
    });
  });
});