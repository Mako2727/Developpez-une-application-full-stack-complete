module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  moduleNameMapper: {
    '@core/(.*)': '<rootDir>/src/app/core/$1',
    '@shared/(.*)': '<rootDir>/src/app/shared/$1', 
    '^src/environments/environment$': '<rootDir>/src/environments/environment.ts' 
  },
  testMatch: ['<rootDir>/src/app/core/**/*.spec.ts'],
  collectCoverage: true,
  coverageDirectory: './coverage/jest',
  coverageReporters: ['text', 'html'],
  coverageThreshold: {
    global: {
      statements: 80,
    },
  },
  testPathIgnorePatterns: ['<rootDir>/node_modules/'],
  coveragePathIgnorePatterns: ['<rootDir>/node_modules/'],
};