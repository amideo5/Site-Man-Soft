export type UserType = 'ADMIN' | 'MANAGER' | 'EMPLOYEE';

export interface User {
  id: string;
  username: string;
  userType: UserType;
}

export interface AuthState {
  token: string | null;  
  user: User | null;
  isAuthenticated: boolean;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}
