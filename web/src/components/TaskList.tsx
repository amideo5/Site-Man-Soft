import React, { useState, useEffect } from "react";
import ReactPaginate from "react-paginate";

interface Task {
  id: number;
  title: string;
  status: string;
}

const TaskList = ({ setActiveTasks }: { setActiveTasks: (count: number) => void }) => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [userDetails, setUserDetails] = useState<{ id: number } | null>(null);
  const tasksPerPage = 3;

  // 🔄 Fetch userDetails from localStorage
  useEffect(() => {
    const userDetailsString = localStorage.getItem("userDetails");
    if (userDetailsString) {
      setUserDetails(JSON.parse(userDetailsString));
    } else {
      const interval = setInterval(() => {
        const storedUser = localStorage.getItem("userDetails");
        if (storedUser) {
          setUserDetails(JSON.parse(storedUser));
          clearInterval(interval); // ✅ Stop polling
        }
      }, 1000);

      return () => clearInterval(interval);
    }
  }, []);

  // 🚀 Fetch tasks when userDetails is available
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

        if (!response.ok) throw new Error("Failed to fetch tasks");

        const data = await response.json();
        const formattedTasks: Task[] = data.map((task: any) => ({
          id: task.id,
          title: task.description,
          status: task.isComplete ? "Completed" : "Pending",
        }));

        setTasks(formattedTasks);

        // ✅ Update active tasks count in the dashboard
        const activeCount = formattedTasks.filter((task) => task.status === "Pending").length;
        setActiveTasks(activeCount);
      } catch (err) {
        setError(err instanceof Error ? err.message : "An unknown error occurred");
      } finally {
        setLoading(false);
      }
    };

    fetchTasks();
  }, [userDetails, setActiveTasks]); // ✅ Depend on `userDetails` and `setActiveTasks`

  // ✅ Pagination logic
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
