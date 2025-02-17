import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/auth';
import { Lock, User } from 'lucide-react';
import logo from '../assets/icon.webp'; // Importing the logo

export function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const { login, isAuthenticated } = useAuthStore();
  const navigate = useNavigate();

  if (isAuthenticated) {
    return <Navigate to="/dashboard" replace />;
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);

    try {
      await login(username, password);
      navigate('/dashboard');
    } catch {
      setError('Invalid credentials');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="dark:bg-gray-800 flex min-h-screen">
      {/* Left Banner */}
      <div className="hidden md:flex flex-col justify-center items-center w-1/2 bg-blue-600 text-white px-10">
        <h1 className="text-4xl font-extrabold">Welcome to TaskFlow</h1>
        <p className="mt-4 text-lg text-gray-200 text-center">
          The ultimate project management tool for teams.
        </p>
        <img
          src="https://images.unsplash.com/photo-1499540633125-484965b60031?q=80&w=2942&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
          alt="Team Work"
          className="mt-6 rounded-lg shadow-lg"
        />
      </div>

      {/* Right Login Form */}
      <div className="flex flex-col justify-center items-center w-full md:w-1/2 px-6 sm:px-10">
        <div className="max-w-md w-full bg-white p-6 sm:p-8 shadow-lg rounded-lg">
          <div className="flex justify-center mb-4">
            <img src={logo} alt="Company Logo" className="h-12 w-12" />
          </div>
          <h2 className="text-center text-3xl font-extrabold text-gray-900">
            Login to Task Flow
          </h2>

          <form className="mt-6 space-y-6" onSubmit={handleSubmit}>
            <div className="space-y-4">
              <div className="relative">
                <User className="absolute left-3 top-3 h-5 w-5 text-gray-400" />
                <input
                  id="username"
                  name="username"
                  type="text"
                  required
                  className="w-full pl-10 px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500"
                  placeholder="Username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>
              <div className="relative">
                <Lock className="absolute left-3 top-3 h-5 w-5 text-gray-400" />
                <input
                  id="password"
                  name="password"
                  type="password"
                  required
                  className="w-full pl-10 px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500"
                  placeholder="Password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            </div>

            {error && <div className="text-red-500 text-sm text-center">{error}</div>}

            <button
              type="submit"
              disabled={isLoading}
              className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition disabled:opacity-50"
            >
              {isLoading ? 'Signing in...' : 'Continue'}
            </button>
          </form>

          {/* Terms and Privacy Policy */}
          <p className="mt-4 text-xs text-gray-600 text-center">
            By logging in, you agree to Task Flow{' '}
            <a href="/terms" className="text-blue-600 hover:underline">Terms of Use</a> {' '}
            and {' '}
            <a href="/privacy" className="text-blue-600 hover:underline">Privacy Policy</a>.
          </p>
        </div>
      </div>
    </div>
  );
}
