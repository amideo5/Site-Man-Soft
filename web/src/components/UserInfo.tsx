import { useEffect, useState } from "react";
import user from "../assets/user.png";

const UserInfo = () => {
  const [userData, setUserData] = useState({
    username: "John Doe",
    designation: "Software Engineer",
    team: "Development",
  });

  useEffect(() => {
    const storedUser = localStorage.getItem("userDetails");
    if (storedUser) {
      try {
        const parsedUser = JSON.parse(storedUser);
        setUserData({
          username: parsedUser.username || "John Doe",
          designation: parsedUser.designation || "Software Engineer",
          team: parsedUser.team || "Development",
        });
      } catch (error) {
        console.error("Error parsing userDetails from localStorage", error);
      }
    }
  }, []);

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm flex items-center gap-4">
      <img src={user} alt="User" className="w-16 h-16 rounded-full border" />
      <div>
        <h2 className="text-xl font-bold">{userData.username}</h2>
        <p className="text-sm text-gray-500">
          {userData.designation} | {userData.team} Team
        </p>
      </div>
    </div>
  );
};

export default UserInfo;
