import { useAuthStore } from '../store/auth';
import EmployeeDashboard from './EmployeeDashboard';
import ManagerDashboard from './ManagerDashboard';
import AdminDashboard from './AdminDashboard';

const Dashboard = () => {
  const { user } = useAuthStore();

  return (
    <div className="p-4 bg-white dark:bg-gray-800 text-gray-900 dark:text-gray-100 min-h-screen">
      {user?.userType === "EMPLOYEE" && <EmployeeDashboard />}
      {user?.userType === "MANAGER" && <ManagerDashboard />}
      {user?.userType === "ADMIN" && <AdminDashboard />}
    </div>
  );
};

export default Dashboard;
