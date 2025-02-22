import React, { useEffect } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuthStore } from "../store/auth";
import { LogOut, Menu, X, Home, Settings, Users, Moon, Sun } from "lucide-react";

export function Layout() {
  const { user, logout } = useAuthStore();
  const [isMobileSidebarOpen, setIsMobileSidebarOpen] = React.useState(false);
  const [isSidebarExpanded, setIsSidebarExpanded] = React.useState(false);
  const [isDarkMode, setIsDarkMode] = React.useState(
    localStorage.getItem("darkMode") === "true" // Load dark mode preference
  );

  useEffect(() => {
    if (isDarkMode) {
      document.documentElement.classList.add("dark"); // Apply dark mode globally
      localStorage.setItem("darkMode", "true");
    } else {
      document.documentElement.classList.remove("dark");
      localStorage.setItem("darkMode", "false");
    }
  }, [isDarkMode]);

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  return (
    <div className="min-h-screen bg-gray-100 dark:bg-gray-900">
      {/* Header */}
      <header className="bg-white dark:bg-gray-800 shadow-sm sticky top-0 z-50">
        <div className="flex items-center justify-between px-4 py-3">
          <div className="flex items-center gap-3">
            {/* Mobile Sidebar Toggle Button */}
            <button
              onClick={() => setIsMobileSidebarOpen(!isMobileSidebarOpen)}
              className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 lg:hidden"
            >
              {isMobileSidebarOpen ? (
                <X className="w-6 h-6 text-gray-600 dark:text-gray-200" />
              ) : (
                <Menu className="w-6 h-6 text-gray-600 dark:text-gray-200" />
              )}
            </button>

            {/* Sidebar Toggle Button */}
            <button
              onClick={() => setIsSidebarExpanded(!isSidebarExpanded)}
              className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 hidden lg:block"
            >
              <Menu className="w-6 h-6 text-gray-600 dark:text-gray-200" />
            </button>

            <h1 className="text-xl font-semibold truncate text-gray-900 dark:text-gray-100">
              Task Flow
            </h1>
          </div>

          <div className="flex items-center gap-4">
            <span className="text-sm text-gray-600 dark:text-gray-200 hidden sm:block">
              {user.fullName}
            </span>
            {/* Dark Mode Toggle Button */}
            <button
              onClick={() => setIsDarkMode(!isDarkMode)}
              className="p-2 text-gray-600 dark:text-gray-200 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700"
              aria-label="Toggle Dark Mode"
            >
              {isDarkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
            </button>
            <button
              onClick={logout}
              className="p-2 text-gray-600 dark:text-gray-200 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700"
              aria-label="Logout"
            >
              <LogOut className="w-5 h-5" />
            </button>
          </div>
        </div>
      </header>

      <div className="flex relative">
        {/* Mobile Overlay */}
        {isMobileSidebarOpen && (
          <div
            className="fixed inset-0 bg-black bg-opacity-50 lg:hidden z-40"
            onClick={() => setIsMobileSidebarOpen(false)}
          />
        )}

        {/* Sidebar */}
        <aside
          className={`
            fixed lg:static inset-y-0 left-0 z-40 bg-white dark:bg-gray-800 shadow-sm
            transition-all duration-300 ease-in-out h-[calc(100vh-px)] overflow-y-auto
            ${isMobileSidebarOpen ? "w-64" : "w-0 lg:w-16"}  
            ${isSidebarExpanded ? "lg:w-64" : "lg:w-16"}
          `}
        >
          <nav className={`p-4 space-y-4 ${isMobileSidebarOpen ? "block" : "hidden lg:block"}`}>
            <SidebarItem icon={Home} label="Home" expanded={isSidebarExpanded || isMobileSidebarOpen} />
            <SidebarItem icon={Users} label="Users" expanded={isSidebarExpanded || isMobileSidebarOpen} />
            <SidebarItem icon={Settings} label="Settings" expanded={isSidebarExpanded || isMobileSidebarOpen} />
          </nav>
        </aside>

        {/* Main Content */}
        <main className="flex-1 p-4 lg:p-6 w-full overflow-x-hidden">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

interface SidebarItemProps {
  icon: React.ElementType;
  label: string;
  expanded: boolean;
  onClick: () => void;
}

function SidebarItem({ icon: Icon, label, expanded, onClick }: SidebarItemProps) {
  return (
    <div
      className={`flex items-center p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 cursor-pointer ${
        expanded ? "justify-start" : "justify-center"
      }`}
      onClick={onClick}
    >
      <Icon className="w-6 h-6 text-gray-600 dark:text-gray-200" />
      {expanded && <span className="ml-3 text-gray-700 dark:text-gray-300">{label}</span>}
    </div>
  );
}

export default SidebarItem;
