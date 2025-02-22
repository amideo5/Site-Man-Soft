import { useEffect, useState } from "react";

const API_URL = `${import.meta.env.VITE_BASE_URL}/api/support-tickets`;

const SupportTickets = ({ setSupportTickets }: { setSupportTickets: (count: number) => void }) => {
  const [tickets, setTickets] = useState([]);
  const [userId, setUserId] = useState<number | null>(null);

  // 🔄 Fetch userDetails from localStorage
  useEffect(() => {
    const userDetailsString = localStorage.getItem("userDetails");
    if (userDetailsString) {
      setUserId(JSON.parse(userDetailsString).id);
    } else {
      const interval = setInterval(() => {
        const storedUser = localStorage.getItem("userDetails");
        if (storedUser) {
          setUserId(JSON.parse(storedUser).id);
          clearInterval(interval); // ✅ Stop polling once found
        }
      }, 500);
      return () => clearInterval(interval); // Cleanup
    }
  }, []);

  // 🚀 Fetch support tickets when userId is available
  useEffect(() => {
    if (!userId) return;
    const token: string = localStorage.getItem("authToken") ?? "";

    const fetchTickets = async () => {
      try {
        const response = await fetch(API_URL, {
          method: "GET",
          credentials: "include",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) throw new Error("Failed to fetch support tickets");

        const data = await response.json();
        const userTickets = data.filter((ticket: any) => ticket.user.id === userId);
        setTickets(userTickets);

        // ✅ Update dashboard ticket count
        setSupportTickets(userTickets.length);
      } catch (error) {
        console.error("Error fetching tickets:", error);
      }
    };

    fetchTickets();
  }, [userId, setSupportTickets]); // ✅ Updates count when tickets change

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
