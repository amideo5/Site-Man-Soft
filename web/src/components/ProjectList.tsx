import React, { useState, useEffect } from "react";

interface Task {
  id: number;
  description: string;
  isComplete: boolean;
}

interface Milestone {
  id: number;
  name: string;
  dueDate: string;
  tasks: Task[];
}

interface ProjectDetails {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
  status: "IN_PROGRESS" | "COMPLETED" | string;
  budget: number;
  tenant: any;
  milestones: Milestone[];
}

interface ProjectAllocation {
  userProjectId: number;
  user: {
    id: number;
    username: string;
    // ...other user properties
  };
  project: ProjectDetails;
  role: string;
  assignedAt: string;
  updatedAt: string;
}

const ProjectList = () => {
  const [projects, setProjects] = useState<ProjectAllocation[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [userDetails, setUserDetails] = useState<{ id: number } | null>(null);

  // 🔄 Poll localStorage every 1 second until userDetails is available
  useEffect(() => {
    const interval = setInterval(() => {
      const userDetailsString = localStorage.getItem("userDetails");
      if (userDetailsString) {
        setUserDetails(JSON.parse(userDetailsString));
        clearInterval(interval); // 🛑 Stop polling once data is found
      }
    }, 1000);
    return () => clearInterval(interval);
  }, []);

  // Fetch projects once userDetails is available
  useEffect(() => {
    if (!userDetails) return;

    const fetchProjects = async () => {
      setLoading(true);
      const token: string = localStorage.getItem("authToken") ?? "";

      try {
        const response = await fetch(
          `${import.meta.env.VITE_BASE_URL}/api/project-users/user/${userDetails.id}`,
          {
            method: "GET",
            credentials: "include",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        if (!response.ok) {
          throw new Error("Failed to fetch projects");
        }

        const data = await response.json();
        setProjects(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An unknown error occurred");
      } finally {
        setLoading(false);
      }
    };

    fetchProjects();
  }, [userDetails]);

  // Calculate project progress.
  // This example computes progress from the tasks in all milestones.
  // If there are no tasks, it defaults to 100% if the project is completed, else 50%.
  const getProgress = (project: ProjectDetails) => {
    const tasks = project.milestones.flatMap((m) => m.tasks);
    const totalTasks = tasks.length;
    if (totalTasks === 0) {
      return project.status === "COMPLETED" ? 100 : 50;
    }
    const completedTasks = tasks.filter((task) => task.isComplete).length;
    return Math.round((completedTasks / totalTasks) * 100);
  };

  if (loading) return <div>Loading projects...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm">
      <h3 className="text-lg font-semibold mb-4">Projects</h3>
      {projects.length > 0 ? (
        <ul>
          {projects.map((allocation) => {
            const { project } = allocation;
            const progress = getProgress(project);
            const progressBarColor =
              project.status === "COMPLETED" ? "bg-green-500" : "bg-yellow-500";

            return (
              <li key={allocation.userProjectId} className="border-b py-4">
                <div className="flex justify-between">
                  <span className="font-medium">{project.name}</span>
                  <span className="text-sm text-gray-500">{project.status}</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-4 mt-2">
                  <div
                    className={`h-4 rounded-full ${progressBarColor}`}
                    style={{ width: `${progress}%` }}
                  />
                </div>
                <p className="text-sm text-gray-600 mt-1">Progress: {progress}%</p>
              </li>
            );
          })}
        </ul>
      ) : (
        <p>No projects found.</p>
      )}
    </div>
  );
};

export default ProjectList;
