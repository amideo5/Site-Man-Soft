import React from 'react';
import DashboardCard from '../components/DashboardCard';
import { Users, FolderOpen, Inbox, ClipboardList } from 'lucide-react';

const ManagerDashboard = () => {
  return (
    <div className="dark:bg-gray-800 space-y-6">
      <h2 className="text-xl sm:text-2xl font-bold text-gray-900">Manager Dashboard</h2>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
        <DashboardCard title="Team Members" icon={Users} value="12" />
        <DashboardCard title="Active Projects" icon={FolderOpen} value="4" />
        <DashboardCard title="Pending Approvals" icon={Inbox} value="7" />
        <DashboardCard title="Task Delegation" icon={ClipboardList} value="15 tasks assigned" />
      </div>

      <div className="bg-white rounded-lg shadow-sm p-4 sm:p-6">
        <h3 className="text-lg font-semibold mb-4">Team Performance</h3>
        <p className="text-sm text-gray-600">Overall productivity is at 85% this week.</p>
        <button className="mt-2 w-full py-2 px-4 bg-green-600 text-white rounded-lg hover:bg-green-700">
          View Reports
        </button>
      </div>
    </div>
  );
};

export default ManagerDashboard;
