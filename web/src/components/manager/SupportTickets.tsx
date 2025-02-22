import { useState, useEffect } from "react";

const SupportTickets = ({ setOpenSupportTickets }) => {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const authToken = localStorage.getItem("authToken");
        const teamMembers = JSON.parse(localStorage.getItem("teamMembers")) || [];
        const teamMemberNames = teamMembers.map((member) => member.name);

        const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/support-tickets`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${authToken}`,
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) throw new Error("Failed to fetch support tickets");

        const data = await response.json();

        // Filter tickets to include only those from team members
        const filteredTickets = data
          .filter((ticket) => teamMemberNames.includes(ticket.user?.username || ""))
          .map((ticket) => ({
            employee: ticket.user?.username || "Unknown",
            issue: ticket.description || "No description provided",
            status: ticket.status || "Pending",
            date: ticket.createdAt ? new Date(ticket.createdAt) : null,
          }));

        setTickets(filteredTickets);
        setOpenSupportTickets(filteredTickets.length);
      } catch (error) {
        console.error("Error fetching support tickets:", error);
        setError("Failed to load support tickets.");
      } finally {
        setLoading(false);
      }
    };

    fetchTickets();
  }, [setOpenSupportTickets]);

  // Function to format the date nicely
  const formatDate = (date) => {
    if (!date) return "Unknown date";
    return new Intl.DateTimeFormat("en-US", { year: "numeric", month: "short", day: "numeric" }).format(date);
  };

  // Function to get status styles
  const getStatusStyle = (status) => {
    switch (status.toLowerCase()) {
      case "open":
        return "bg-green-100 text-green-600";
      case "in progress":
        return "bg-yellow-100 text-yellow-600";
      case "closed":
        return "bg-red-100 text-red-600";
      default:
        return "bg-gray-100 text-gray-600";
    }
  };

  return (
    <div className="bg-white dark:bg-gray-700 p-4 rounded-lg shadow">
      <h2 className="text-lg font-semibold mb-4">Support Tickets</h2>

      {loading ? (
        <p className="text-gray-500">Loading support tickets...</p>
      ) : error ? (
        <p className="text-red-500">{error}</p>
      ) : tickets.length === 0 ? (
        <p className="text-gray-500">No open support tickets.</p>
      ) : (
        <ul className="divide-y divide-gray-300 dark:divide-gray-600">
          {tickets.map((ticket, index) => (
            <li key={index} className="py-3 flex justify-between items-center">
              <div>
                <span className="block text-gray-800 dark:text-gray-200 font-medium">
                  {ticket.employee}
                </span>
                <span className="block text-sm text-gray-600 dark:text-gray-400">
                  {ticket.issue}
                </span>
                <span className="block text-xs text-gray-500">{formatDate(ticket.date)}</span>
              </div>
              <div className="flex items-center gap-4">
                <span className={`px-2 py-1 text-xs rounded ${getStatusStyle(ticket.status)}`}>
                  {ticket.status}
                </span>
                <button className="text-blue-500 hover:underline">View</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default SupportTickets;
