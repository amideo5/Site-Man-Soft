import DashboardCard from "../components/DashboardCard";
import { Clock, FileText, Inbox, ListChecks } from "lucide-react";
import TimeTracker from "../components/TimeTracker";
import TaskList from "../components/TaskList";
import ResourceList from "../components/ResourceList";
import ProjectList from "../components/ProjectList";
import SupportTickets from "../components/SupportTickets";
import UserInfo from "../components/UserInfo";

const EmployeeDashboard = () => {
  return (
    <div className="dark:bg-gray-800 space-y-6 p-4 sm:p-6 bg-gray-50 min-h-screen">
      {/* User Info */}
      <UserInfo />

      {/* Dashboard Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <DashboardCard title="Time Tracked Today" icon={Clock} value="6h 30m" />
        <DashboardCard title="Active Tasks" icon={FileText} value="5" />
        <DashboardCard title="Resource Requests" icon={Inbox} value="2" />
        <DashboardCard title="Completed Tasks" icon={ListChecks} value="12" />
      </div>

      {/* Sections */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <TimeTracker />
        <TaskList />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ResourceList />
        <ProjectList />
      </div>

      <SupportTickets />
    </div>
  );
};

export default EmployeeDashboard;
