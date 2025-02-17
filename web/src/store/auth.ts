import { create } from 'zustand';
import { AuthState, User } from '../types/auth';

const apiLogin = async (username: string, password: string): Promise<{ token: string; user: User }> => {
  const loginResponse = await fetch(`${import.meta.env.VITE_BASE_URL}/api/users/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ username, password }),
    credentials: 'include'
  });

  if (!loginResponse.ok) {
    throw new Error('Invalid credentials');
  }

  const { token } = await loginResponse.json();

  // Extract user details from token OR make another API call to fetch user data
  const user = parseJwt(token); // Function to decode JWT token

  if (!user) {
    throw new Error('Failed to extract user details from token');
  }

  return { token, user };
};

// Function to decode JWT token and extract user information
const parseJwt = (token: string): User | null => {
  try {
    const base64Url = token.split('.')[1]; // Extract payload part of JWT
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map((c) => `%${('00' + c.charCodeAt(0).toString(16)).slice(-2)}`)
        .join('')
    );

    const decoded = JSON.parse(jsonPayload);

    return {
      id: decoded.sub, // Assumes 'sub' contains user ID
      username: decoded.sub, // Adjust based on token structure
      userType: decoded.role as 'ADMIN' | 'MANAGER' | 'EMPLOYEE', // Ensure type safety
    };
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
};

export const useAuthStore = create<AuthState>((set) => ({
  token: localStorage.getItem('authToken') || null,
  user: localStorage.getItem('authUser') ? JSON.parse(localStorage.getItem('authUser')!) : null,
  isAuthenticated: !!localStorage.getItem('authToken'),

  login: async (username: string, password: string) => {
    try {
      const { token, user } = await apiLogin(username, password);
      
      // Store token and user in localStorage
      localStorage.setItem('authToken', token);
      localStorage.setItem('authUser', JSON.stringify(user));

      set({ token, user, isAuthenticated: true });

      const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/users/get/${user.username}`, {
        method: 'GET',
        credentials: 'include',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch user details');
      }

      const userDetails = await response.json();
      console.log('Fetched User Details:', userDetails);
      localStorage.setItem('userDetails', JSON.stringify(userDetails));
    } catch (error) {
      console.error(error);
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('authUser');
    localStorage.removeItem('userDetails');
    set({ token: null, user: null, isAuthenticated: false });
  }
}));
