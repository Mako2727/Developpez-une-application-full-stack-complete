export interface SessionInformation {
  token: string;
  username: string;
  id?: number;      // optionnel
  firstName?: string;
  lastName?: string;
  admin?: boolean;
}