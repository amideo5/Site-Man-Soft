import React, { useState, useEffect } from "react";
import type { Timeout } from "node:timers";

const TimeTracker = () => {
  const [tracking, setTracking] = useState(false);
  const [clockInTime, setClockInTime] = useState<Date | null>(null);
  const [elapsedTime, setElapsedTime] = useState(0);
  const [location, setLocation] = useState<{ lat: number; lon: number } | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);

  // Effect to get and set the username from localStorage when component mounts.
  useEffect(() => {
    const userDetailsStr = localStorage.getItem("userDetails");
    if (userDetailsStr) {
      const { id, username } = JSON.parse(userDetailsStr);
      setUsername(username);
    }
  }, []);

  // Effect to check for saved tracking info when username becomes available.
  useEffect(() => {
    if (!username) return;
    const storedTrackingInfo = localStorage.getItem(`tracking_${username}`);
    if (storedTrackingInfo) {
      const { clockInTime: storedClockIn, date: storedDate } = JSON.parse(storedTrackingInfo);
      const storedClockInTime = new Date(storedClockIn);
      const todayDate = new Date().toISOString().split("T")[0];

      if (storedDate !== todayDate) {
        // Auto clock-out if the stored date is not today.
        console.log("Auto clocking out due to date change...");
        localStorage.removeItem(`tracking_${username}`);
      } else {
        setClockInTime(storedClockInTime);
        setTracking(true);
        setElapsedTime(Math.floor((Date.now() - storedClockInTime.getTime()) / 1000));
      }
    }
  }, [username]);

  // Effect to update the elapsed time every second if tracking.
  useEffect(() => {
    let interval: Timeout | null = null;
    if (tracking && clockInTime) {
      interval = setInterval(() => {
        setElapsedTime(Math.floor((Date.now() - clockInTime.getTime()) / 1000));
      }, 1000);
    } else {
      setElapsedTime(0);
    }
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [tracking, clockInTime]);

  // Effect to clear error messages after 3 seconds.
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(null), 3000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  // Effect to auto clock-out at midnight if tracking.
  useEffect(() => {
    let autoClockOutTimeout: Timeout | null = null;
    if (tracking && clockInTime) {
      const now = new Date();
      const midnight = new Date(now);
      midnight.setHours(24, 0, 0, 0);
      const timeUntilMidnight = midnight.getTime() - now.getTime();

      console.log(`Auto clock-out scheduled in ${timeUntilMidnight / 1000} seconds`);

      autoClockOutTimeout = setTimeout(() => {
        console.log("Auto clocking out at midnight...");
        handleClockOut();
      }, timeUntilMidnight);
    }

    return () => {
      if (autoClockOutTimeout) clearTimeout(autoClockOutTimeout);
    };
  }, [tracking, clockInTime]);

  const fetchLocation = (): Promise<{ lat: number; lon: number }> => {
    return new Promise((resolve, reject) => {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const { latitude, longitude } = position.coords;
            resolve({ lat: latitude, lon: longitude });
          },
          (error) => {
            console.error("Error getting location:", error);
            reject(error);
          }
        );
      } else {
        reject(new Error("Geolocation is not supported by this browser."));
      }
    });
  };

  const handleClockIn = async () => {
    setErrorMessage(null);
    try {
      const token = localStorage.getItem("authToken");
      const userDetailsStr = localStorage.getItem("userDetails");
      if (!token || !userDetailsStr) {
        throw new Error("User not authenticated.");
      }
      const { id: userId, username } = JSON.parse(userDetailsStr);
      setUsername(username);

      const location = await fetchLocation();

      const response = await fetch(
        `${import.meta.env.VITE_BASE_URL}/api/time-tracker/clock-in`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            userId,
            latitude: location.lat,
            longitude: location.lon,
          }),
        }
      );

      if (!response.ok) {
        let errorMsg = "Clock-in failed.";
        try {
          const errorJson = await response.json();
          errorMsg = errorJson.error || errorMsg;
        } catch {}
        throw new Error(errorMsg);
      }

      const now = new Date();
      const todayDate = now.toISOString().split("T")[0];

      setTracking(true);
      setClockInTime(now);
      setLocation(location);

      localStorage.setItem(
        `tracking_${username}`,
        JSON.stringify({ clockInTime: now.toISOString(), date: todayDate, userId })
      );
    } catch (error: any) {
      console.error("Error during clock-in:", error);
      setErrorMessage(error.message || "An unknown error occurred");
    }
  };

  const handleClockOut = async () => {
    setErrorMessage(null);
    try {
      const token = localStorage.getItem("authToken");
      const userDetailsStr = localStorage.getItem("userDetails");
      if (!token || !userDetailsStr) {
        throw new Error("User not authenticated.");
      }
      const { id: userId, username } = JSON.parse(userDetailsStr);

      const response = await fetch(
        `${import.meta.env.VITE_BASE_URL}/api/time-tracker/clock-out`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ userId }),
        }
      );

      if (!response.ok) {
        let errorMsg = "Clock-out failed.";
        try {
          const errorJson = await response.json();
          errorMsg = errorJson.error || errorMsg;
        } catch {}
        throw new Error(errorMsg);
      }

      setTracking(false);
      setClockInTime(null);
      setElapsedTime(0);
      setLocation(null);

      localStorage.removeItem(`tracking_${username}`);
    } catch (error: any) {
      console.error("Error during clock-out:", error);
      setErrorMessage(error.message || "An unknown error occurred");
    }
  };

  const handleClockToggle = () => {
    if (tracking) {
      handleClockOut();
    } else {
      handleClockIn();
    }
  };

  const formatElapsedTime = (seconds: number): string => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    return `${hours}h ${minutes}m ${secs}s`;
  };

  return (
    <div className="dark:bg-gray-800 bg-white p-6 rounded-lg shadow-sm">
      <h3 className="text-lg font-semibold mb-4">Time Tracking</h3>
      <button
        onClick={handleClockToggle}
        className={`w-full py-2 px-4 rounded-lg ${
          tracking ? "bg-red-600 hover:bg-red-700" : "bg-blue-600 hover:bg-blue-700"
        } text-white`}
      >
        {tracking ? "Clock Out" : "Clock In"}
      </button>

      {errorMessage && (
        <div className="text-red-600 mt-2 text-sm">{errorMessage}</div>
      )}

      {clockInTime && (
        <div className="text-sm text-gray-600 mt-2">
          Clocked in at: {clockInTime.toLocaleTimeString()}
        </div>
      )}

      {tracking && (
        <div className="text-sm text-gray-800 mt-2">
          Elapsed Time: {formatElapsedTime(elapsedTime)}
        </div>
      )}
    </div>
  );
};

export default TimeTracker;
