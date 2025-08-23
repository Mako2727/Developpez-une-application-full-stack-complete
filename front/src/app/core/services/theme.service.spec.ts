/// <reference types="jest" />

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ThemeService } from './theme.service';
import { environment } from 'src/environments/environment';
import { MessageResponse } from '../../shared/models/messageResponse.interface';
import { Theme } from '../../shared/models/theme.interface';

describe('ThemeService', () => {
  let service: ThemeService;
  let httpMock: HttpTestingController;

  beforeAll(() => {
    // Mock complet de localStorage
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

    // On ajoute un token pour les tests
    localStorage.setItem('token', 'mock-token');
  });

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ThemeService]
    });
    service = TestBed.inject(ThemeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should get themes', () => {
    const mockThemes: Theme[] = [{ id: 1, name: 'Angular' } as Theme];

    service.getThemes().subscribe(themes => {
      expect(themes).toEqual(mockThemes);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}api/topics`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');

    req.flush(mockThemes);
  });

  it('should subscribe to a topic', () => {
    const mockResponse: MessageResponse = { message: 'Subscribed' } as MessageResponse;
    const topicId = 1;

    service.subscribe(topicId).subscribe(res => {
      expect(res).toEqual(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}api/topics/${topicId}/subscribe`);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');
    expect(req.request.body).toEqual({});

    req.flush(mockResponse);
  });

  it('should unsubscribe from a topic', () => {
    const topicId = 1;
    const mockResponse = 'Unsubscribed';

    service.unsubscribe(topicId).subscribe(res => {
      expect(res).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}api/topics/${topicId}`);
    expect(req.request.method).toBe('DELETE');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');

    req.flush(mockResponse);
  });

  it('should get subscribed themes', () => {
    const mockThemes: Theme[] = [{ id: 2, name: 'React' } as Theme];

    service.getSubscribedTheme().subscribe(themes => {
      expect(themes).toEqual(mockThemes);
    });

    const req = httpMock.expectOne(`${environment.baseUrl}api/topics/subscribed`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe('Bearer mock-token');

    req.flush(mockThemes);
  });
});