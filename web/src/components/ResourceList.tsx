import React, { useState, useEffect } from "react";

interface Resource {
  id: number;
  name: string;
  allocatedAt: string;
  returnDate: string;
  allocatedQuantity: number;
}

const ResourceList = ({ setResourceRequests }: { setResourceRequests: (count: number) => void }) => {
  const [resources, setResources] = useState<Resource[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [userDetails, setUserDetails] = useState<{ id: number } | null>(null);

  // 🔄 Fetch userDetails from localStorage (checks every 1s)
  useEffect(() => {
    const userDetailsString = localStorage.getItem("userDetails");
    if (userDetailsString) {
      setUserDetails(JSON.parse(userDetailsString));
    } else {
      const interval = setInterval(() => {
        const storedUser = localStorage.getItem("userDetails");
        if (storedUser) {
          setUserDetails(JSON.parse(storedUser));
          clearInterval(interval); // ✅ Stop polling once found
        }
      }, 1000);

      return () => clearInterval(interval); // Cleanup
    }
  }, []);

  // 🚀 Fetch resources when userDetails is available
  useEffect(() => {
    if (!userDetails) return;

    const fetchResources = async () => {
      setLoading(true);
      const token: string = localStorage.getItem("authToken") ?? "";

      try {
        const response = await fetch(
          `${import.meta.env.VITE_BASE_URL}/api/resource-allocations/user/${userDetails.id}`,
          {
            method: "GET",
            credentials: "include",
            headers: {
              Authorization: `Bearer ${token}`,
              "Content-Type": "application/json",
            },
          }
        );

        if (!response.ok) throw new Error("Failed to fetch resources");

        const data = await response.json();
        const formattedResources: Resource[] = data.map((resource: any) => ({
          id: resource.id,
          name: resource.resourceName,
          allocatedAt: new Date(resource.allocatedAt).toLocaleDateString(),
          returnDate: new Date(resource.returnDate).toLocaleDateString(),
          allocatedQuantity: resource.allocatedQuantity,
        }));

        setResources(formattedResources);
        setResourceRequests(formattedResources.length); // ✅ Update dashboard count
      } catch (err) {
        setError(err instanceof Error ? err.message : "An unknown error occurred");
      } finally {
        setLoading(false);
      }
    };

    fetchResources();
  }, [userDetails, setResourceRequests]); // ✅ Includes setResourceRequests in dependencies

  if (loading) return <div>Loading resources...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm">
      <h3 className="text-lg font-semibold mb-4">Resources</h3>
      <ul>
        {resources.length > 0 ? (
          resources.map((resource) => (
            <li key={resource.id} className="border-b py-2 flex justify-between">
              <span className="font-medium">{resource.name}</span>
              <span className="text-gray-500">Quantity: {resource.allocatedQuantity}</span>
            </li>
          ))
        ) : (
          <p>No resources allocated.</p>
        )}
      </ul>
    </div>
  );
};

export default ResourceList;
