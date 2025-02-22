import DashboardCard from '../components/DashboardCard';
import { Users, Database, BarChart2, Server } from 'lucide-react';

const AdminDashboard = () => {
  return (
    <div className="dark:bg-gray-800 space-y-6">
      <h2 className="text-xl sm:text-2xl font-bold text-gray-900">Admin Dashboard</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
        <DashboardCard title="Total Users" icon={Users} value="156" />
        <DashboardCard title="Database Usage" icon={Database} value="72%" />
        <DashboardCard title="System Health" icon={Server} value="98%" />
        <DashboardCard title="Resource Allocation" icon={BarChart2} value="85%" />
      </div>

      <div className="dark:bg-gray-800 bg-white rounded-lg shadow-sm p-4 sm:p-6">
        <h3 className="text-lg font-semibold mb-4">System Overview</h3>
        <p className="text-sm text-gray-600">All systems are running smoothly.</p>
      </div>
    </div>
  );
};

export default AdminDashboard;
