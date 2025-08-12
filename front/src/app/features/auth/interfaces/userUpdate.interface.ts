export interface UserUpdate {
  email: string;
  username: string;
  password?: string; // facultatif si pas modifié
}