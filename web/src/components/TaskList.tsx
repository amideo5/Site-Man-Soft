import React, { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

interface Task {
  id: number;
  title: string;
  status: string;
}

const TaskList = () => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [userDetails, setUserDetails] = useState<{ id: number } | null>(null);
  const tasksPerPage = 3;

  // 🔄 Poll localStorage every 1 second until userDetails is available
  useEffect(() => {
    const interval = setInterval(() => {
      const userDetailsString = localStorage.getItem("userDetails");
      if (userDetailsString) {
        setUserDetails(JSON.parse(userDetailsString));
        clearInterval(interval); // 🛑 Stop polling once data is found
      }
    }, 1000); // Polling interval: 1 second

    return () => clearInterval(interval); // Cleanup interval on unmount
  }, []);

  // Fetch tasks once userDetails is available
  useEffect(() => {
    if (!userDetails) return;

    const fetchTasks = async () => {
      setLoading(true);
      const token: string = localStorage.getItem("authToken") ?? "";

      try {
        const response = await fetch(
          `${import.meta.env.VITE_BASE_URL}/api/tasks/user/${userDetails.id}`,
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
          throw new Error("Failed to fetch tasks");
        }

        const data = await response.json();
        const formattedTasks: Task[] = data.map((task: any) => ({
          id: task.id,
          title: task.description,
          status: task.isComplete ? "Completed" : "Pending",
        }));

        setTasks(formattedTasks);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An unknown error occurred");
      } finally {
        setLoading(false);
      }
    };

    fetchTasks();
  }, [userDetails]); // ✅ Fetch tasks when userDetails becomes available

  const pageCount = Math.ceil(tasks.length / tasksPerPage);
  const currentTasks = tasks.slice(currentPage * tasksPerPage, (currentPage + 1) * tasksPerPage);

  if (loading) return <div>Loading tasks...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm">
      <h3 className="text-lg font-semibold mb-4">Tasks</h3>
      <ul>
        {currentTasks.map((task) => (
          <li key={task.id} className="border-b py-2 flex justify-between">
            {task.title} <span className="text-gray-500">{task.status}</span>
          </li>
        ))}
      </ul>
      <ReactPaginate
        previousLabel={"←"}
        nextLabel={"→"}
        pageCount={pageCount}
        onPageChange={({ selected }) => setCurrentPage(selected)}
        containerClassName={"pagination flex justify-center mt-4 space-x-2"}
        activeClassName={"text-blue-600 font-bold"}
      />
    </div>
  );
};

export default TaskList;
