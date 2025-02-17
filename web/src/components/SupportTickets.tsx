import { useEffect, useState } from "react";

const API_URL = `${import.meta.env.VITE_BASE_URL}/api/support-tickets`;

const SupportTickets = () => {
  const [tickets, setTickets] = useState([]);
  const [newTicket, setNewTicket] = useState("");
  const [userId, setUserId] = useState(null);

  // Poll localStorage for user details
  useEffect(() => {
    const interval = setInterval(() => {
      const userDetailsString = localStorage.getItem("userDetails");
      if (userDetailsString) {
        const user = JSON.parse(userDetailsString);
        setUserId(user.id);
        clearInterval(interval); // Stop polling once user ID is available
      }
    }, 500); // Poll every 500ms

    return () => clearInterval(interval); // Cleanup on unmount
  }, []);

  // Fetch tickets once userId is available
  useEffect(() => {
    if (!userId) return;
    const token: string = localStorage.getItem("authToken") ?? "";

    const fetchTickets = async () => {
      try {
        const response = await fetch(
          API_URL,
          {
            method: "GET",
            credentials: "include",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );
        const data = await response.json();

        // Filter tickets belonging to the current user
        const userTickets = data.filter(ticket => ticket.user.id === userId);
        setTickets(userTickets);
      } catch (error) {
        console.error("Error fetching tickets:", error);
      }
    };

    fetchTickets();
  }, [userId]);

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm">
      <h3 className="text-lg font-semibold mb-4">Support Tickets</h3>

      {/* Ticket List */}
      <ul className="space-y-2">
        {tickets.length > 0 ? (
          tickets.map((ticket) => (
            <li key={ticket.id} className="border-b py-3 flex justify-between">
              <div>
                <p className="font-medium">{ticket.description}</p>
                <p className="text-xs text-gray-500">
                  Date: {ticket.createdAt.split("T")[0]}
                </p>
              </div>
              <span
                className={`text-sm px-2 py-1 rounded-full ${
                  ticket.status === "RESOLVED"
                    ? "bg-green-100 text-green-600"
                    : "bg-yellow-100 text-yellow-600"
                }`}
              >
                {ticket.status}
              </span>
            </li>
          ))
        ) : (
          <p className="text-gray-500 text-sm">No support tickets found.</p>
        )}
      </ul>
    </div>
  );
};

export default SupportTickets;
