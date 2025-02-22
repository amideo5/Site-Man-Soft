import { useState, useEffect } from "react";

const TeamOverview = () => {
  const [teamMembers, setTeamMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [userDetails, setUserDetails] = useState<{ id: number } | null>(null);

  // ✅ Load user details from localStorage
  useEffect(() => {
    const userDetailsString = localStorage.getItem("userDetails");

    if (userDetailsString) {
      setUserDetails(JSON.parse(userDetailsString));
    } else {
      const checkUser = () => {
        const storedUser = localStorage.getItem("userDetails");
        if (storedUser) {
          setUserDetails(JSON.parse(storedUser));
        } else {
          setTimeout(checkUser, 1000); // ✅ Prevent memory leaks
        }
      };
      checkUser();
    }
  }, []);

  // ✅ Fetch team members from API and store in localStorage
  useEffect(() => {
    const fetchTeamMembers = async () => {
      if (!userDetails) return;

      try {
        setLoading(true);
        setError(null);

        const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/users/manager/${userDetails.id}/employees`);
        if (!response.ok) throw new Error("Failed to fetch team members");

        const data = await response.json();

        // Transform API response into required format
        const formattedData = await Promise.all(
          data.map(async (member) => {
            const timeTracked = await fetchClockInTime(member.id);
            return {
              name: member.username,
              status: timeTracked ? "Online" : "Offline",
              timeTracked: timeTracked || "N/A",
            };
          })
        );

        // ✅ Store fetched team members in localStorage
        localStorage.setItem("teamMembers", JSON.stringify(formattedData));

        // ✅ Update state with fresh API data
        setTeamMembers(formattedData);
      } catch (error) {
        setError(error?.message || "An unknown error occurred");
      } finally {
        setLoading(false);
      }
    };

    fetchTeamMembers(); // ✅ Always fetch fresh data
  }, [userDetails]); // ✅ Re-fetch when userDetails updates

  // ✅ Fetch clock-in time for a user
  const fetchClockInTime = async (userId) => {
    try {
      const authToken = localStorage.getItem("authToken"); // 🔐 Retrieve token from localStorage
  
      if (!authToken) {
        console.error("No auth token found");
        return null;
      }
  
      const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/time-tracker/user/${userId}`, {
        method: "GET",
        headers: {
          "Authorization": `Bearer ${authToken}`, // 🔐 Include Bearer token
          "Content-Type": "application/json",
        },
      });
  
      if (!response.ok) throw new Error("Failed to fetch clock-in data");
  
      const data = await response.json();
      const today = new Date().toISOString().split("T")[0]; // Get today's date in YYYY-MM-DD format
  
      const todayClockIn = data.find((entry) => entry.clockInDate === today);
      if (!todayClockIn) return null;
  
      return calculateElapsedTime(todayClockIn.clockInTime);
    } catch (error) {
      console.error("Error fetching clock-in data:", error);
      return null;
    }
  };

  // ✅ Calculate elapsed time since clock-in
  const calculateElapsedTime = (clockInTime) => {
    const clockInDate = new Date(clockInTime);
    const now = new Date();
    const elapsedMilliseconds = now - clockInDate;

    const hours = Math.floor(elapsedMilliseconds / 3600000);
    const minutes = Math.floor((elapsedMilliseconds % 3600000) / 60000);

    return `${hours}h ${minutes}m`;
  };

  return (
    <div className="bg-white dark:bg-gray-700 p-4 rounded-lg shadow">
      <h2 className="text-lg font-semibold mb-4">Team Overview</h2>

      {loading && <p>Loading...</p>}
      {error && <p className="text-red-500">{error}</p>}

      {!loading && !error && (
        <ul>
          {teamMembers.map((member, index) => (
            <li key={index} className="flex justify-between py-2 border-b last:border-none">
              <span>{member.name}</span>
              <span className={`text-sm ${member.status === "Online" ? "text-green-500" : "text-gray-500"}`}>
                {member.status}
              </span>
              <span>{member.timeTracked}</span>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default TeamOverview;
