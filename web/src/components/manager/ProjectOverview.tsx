import { useState, useEffect } from "react";

const ProjectOverview = () => {
  const [projects, setProjects] = useState([]);
  const [authToken, setAuthToken] = useState(localStorage.getItem("authToken")); // Store authToken in state

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        if (!authToken) {
          console.error("No auth token found.");
          return;
        }

        const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/projects`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${authToken}`,
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch projects");
        }

        const data = await response.json();

        // Get team members from localStorage
        const teamMembers = JSON.parse(localStorage.getItem("teamMembers")) || [];
        const teamMemberNames = teamMembers.map((member) => member.name);

        // Filter projects where the logged-in user is in `tenant.users`
        const filteredProjects = data.filter((project) =>
          project.tenant.users.some((user) => teamMemberNames.includes(user.username))
        );

        // Transform data to extract relevant details
        const formattedProjects = filteredProjects.map((project) => ({
          name: project.name,
          progress: project.status === "IN_PROGRESS" ? "In Progress" : "Completed",
        }));

        setProjects(formattedProjects);
      } catch (error) {
        console.error("Error fetching projects:", error);
      }
    };

    fetchProjects();
  }, [authToken]); // ✅ Dependencies are correctly used

  return (
    <div className="bg-white dark:bg-gray-700 p-4 rounded-lg shadow">
      <h2 className="text-lg font-semibold mb-4">Project Overview</h2>
      {projects.length > 0 ? (
        <ul>
          {projects.map((project, index) => (
            <li key={index} className="py-2 border-b last:border-none flex justify-between">
              <span>{project.name}</span>
              <span className="text-sm text-blue-500">{project.progress}</span>
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-gray-500">No projects available.</p>
      )}
    </div>
  );
};

export default ProjectOverview;
