import { useState, useEffect } from "react";
import DashboardCard from "../components/DashboardCard";
import { Clock, FileText, Inbox, ListChecks } from "lucide-react";
import TimeTracker from "../components/TimeTracker";
import TaskList from "../components/TaskList";
import ResourceList from "../components/ResourceList";
import ProjectList from "../components/ProjectList";
import SupportTickets from "../components/SupportTickets";
import UserInfo from "../components/UserInfo";

const EmployeeDashboard = () => {
  // State variables to store dynamic values
  const [timeTracked, setTimeTracked] = useState("0h 0m");
  const [activeTasks, setActiveTasks] = useState(0);
  const [resourceRequests, setResourceRequests] = useState(0);
  const [supportTickets, setsupportTickets] = useState(0);

  return (
    <div className="dark:bg-gray-800 space-y-6 p-4 sm:p-6 bg-gray-50 min-h-screen">
      {/* User Info */}
      <UserInfo />

      {/* Dashboard Cards with Dynamic Values */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
        <DashboardCard title="Time Tracked Today" icon={Clock} value={timeTracked} />
        <DashboardCard title="Active Tasks" icon={FileText} value={activeTasks} />
        <DashboardCard title="Resource Requests" icon={Inbox} value={resourceRequests} />
        <DashboardCard title="Support Tickets" icon={ListChecks} value={supportTickets} />
      </div>

      {/* Sections */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <TimeTracker setTimeTracked={setTimeTracked} />
        <TaskList setActiveTasks={setActiveTasks} />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ResourceList setResourceRequests={setResourceRequests} />
        <ProjectList />
      </div>

      <SupportTickets setSupportTickets={setsupportTickets} />
      </div>
  );
};

export default EmployeeDashboard;
