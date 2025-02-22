import { useState, useEffect } from "react";

const ResourceOverview = ({ setAllocatedResources }) => {
  const [resources, setResources] = useState([]);
  const [authToken, setAuthToken] = useState(localStorage.getItem("authToken"));

  useEffect(() => {
    const fetchResources = async () => {
      try {
        if (!authToken) {
          console.error("No auth token found.");
          return;
        }

        const response = await fetch(`${import.meta.env.VITE_BASE_URL}/api/resources`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${authToken}`,
            "Content-Type": "application/json",
          },
        });

        if (!response.ok) {
          throw new Error("Failed to fetch resources");
        }

        const data = await response.json();

        const teamMembers = JSON.parse(localStorage.getItem("teamMembers")) || [];
        const teamMemberNames = teamMembers.map((member) => member.name);

        const filteredResources = data
          .map((resource) => ({
            name: resource.name,
            allocations: resource.allocations.filter((allocation) =>
              teamMemberNames.includes(allocation.user.username)
            ),
          }))
          .filter((resource) => resource.allocations.length > 0);

        setResources(filteredResources);

        // Calculate total allocated resources count
        const totalAllocations = filteredResources.reduce(
          (sum, resource) => sum + resource.allocations.length,
          0
        );

        setAllocatedResources(totalAllocations);
      } catch (error) {
        console.error("Error fetching resources:", error);
      }
    };

    fetchResources();
  }, [authToken, setAllocatedResources]);

  return (
    <div className="bg-white dark:bg-gray-700 p-4 rounded-lg shadow">
      <h2 className="text-lg font-semibold mb-4">Resource Overview</h2>
      {resources.length > 0 ? (
        <ul>
          {resources.map((resource, index) => (
            <li key={index} className="py-2 border-b last:border-none">
              <h3 className="font-medium">{resource.name}</h3>
              <ul className="ml-4 mt-2">
                {resource.allocations.map((allocation, subIndex) => (
                  <li key={subIndex} className="text-sm text-gray-600 flex justify-between">
                    <span>{allocation.user.username}</span>
                    <span className="text-blue-500">Qty: {allocation.allocatedQuantity}</span>
                  </li>
                ))}
              </ul>
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-gray-500">No resources allocated to your team.</p>
      )}
    </div>
  );
};

export default ResourceOverview;
