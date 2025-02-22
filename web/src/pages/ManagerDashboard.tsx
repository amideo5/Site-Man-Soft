import { useState, useEffect } from "react";
import DashboardCard from "../components/DashboardCard";
import { Clock, FileText, Users, ClipboardList, Inbox } from "lucide-react";
import TeamOverview from "../components/manager/TeamOverview";
import TaskManager from "../components/manager/TaskManager";
import ProjectOverview from "../components/manager/ProjectOverview";
import ResourceApprovals from "../components/manager/ResourceApprovals";
import SupportTickets from "../components/manager/SupportTickets";
import Reports from "../components/manager/Reports";
import Notifications from "../components/manager/Notifications";

const ManagerDashboard = () => {
  // State variables for aggregated data
  const [totalTrackedHours, setTotalTrackedHours] = useState("0h 0m");
  const [teamTasks, setTeamTasks] = useState(0);
  const [allocatedResources, setAllocatedResources] = useState(0);
  const [openSupportTickets, setOpenSupportTickets] = useState(0);

  return (
    <div className="dark:bg-gray-800 space-y-6 p-4 sm:p-6 bg-gray-50 min-h-screen">
      {/* Team Overview */}
      <TeamOverview />

      {/* Dashboard Cards with Summary Metrics */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
        {/* <DashboardCard title="Total Time Tracked" icon={Clock} value={totalTrackedHours} /> */}
        <DashboardCard title="Active Team Tasks" icon={FileText} value={teamTasks} />
        <DashboardCard title="Resource Requests" icon={Inbox} value={allocatedResources} />
        <DashboardCard title="Open Support Tickets" icon={ClipboardList} value={openSupportTickets} />
      </div>

      {/* Main Dashboard Sections */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <TaskManager setTeamTasks={setTeamTasks} />
        <ProjectOverview />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <ResourceApprovals setAllocatedResources={setAllocatedResources} />
        <SupportTickets setOpenSupportTickets={setOpenSupportTickets} />
      </div>

      {/* Additional Sections */}
      <Reports />
      <Notifications />
    </div>
  );
};

export default ManagerDashboard;
