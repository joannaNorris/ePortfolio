import { TestBed } from '@angular/core/testing';
import { HttpInterceptor } from '@angular/common/http';

import { JwtInterceptor } from './jwt-interceptor';

describe('JwtInterceptor', () => {
  const interceptor: HttpInterceptor = {
    intercept: (req, next) =>
      TestBed.runInInjectionContext(
        () => new JwtInterceptor({} as any).intercept(req, next),
      ),
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });
});
