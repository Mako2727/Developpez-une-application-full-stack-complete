module.exports = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  moduleNameMapper: {
    '@core/(.*)': '<rootDir>/src/app/core/$1',
    '@shared/(.*)': '<rootDir>/src/app/shared/$1', // ajoute si besoin
    '^src/environments/environment$': '<rootDir>/src/environments/environment.ts' // <- mapping env
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