import { useState, useEffect } from "react";

const TaskManager = ({ setTeamTasks }) => {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const authToken = localStorage.getItem("authToken");
        const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/tasks`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${authToken}`, // 🔐 Include Bearer token
            "Content-Type": "application/json",
          },
        });
        const data = await response.json();

        // Get team members from localStorage
        const teamMembers = JSON.parse(localStorage.getItem("teamMembers")) || [];
        const teamMemberNames = teamMembers.map((member) => member.name);

        // Filter tasks where assignedTo username matches localStorage team members
        const filteredTasks = data.filter((task) =>
          task.assignedTo && teamMemberNames.includes(task.assignedTo.username)
        );

        setTasks(filteredTasks);
        setTeamTasks(filteredTasks.length);
      } catch (error) {
        console.error("Error fetching tasks:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchTasks();
  }, [setTeamTasks]);

  return (
    <div className="bg-white dark:bg-gray-700 p-4 rounded-lg shadow">
      <h2 className="text-lg font-semibold mb-4">Task Manager</h2>

      {loading ? (
        <p>Loading tasks...</p>
      ) : tasks.length === 0 ? (
        <p>No tasks assigned.</p>
      ) : (
        <ul>
          {tasks.map((task) => (
            <li key={task.id} className="py-2 border-b last:border-none flex justify-between items-center">
              <div>
                <span className="block font-medium">{task.description}</span>
                <span className="text-sm text-gray-500">
                  Assigned to: <strong>{task.assignedTo?.username || "Unassigned"}</strong>
                </span>
              </div>
              <span className={`text-sm ${task.isComplete ? "text-green-500" : "text-red-500"}`}>
                {task.isComplete ? "Completed" : "Pending"}
              </span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default TaskManager;
