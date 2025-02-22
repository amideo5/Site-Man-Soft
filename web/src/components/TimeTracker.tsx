import React, { useState, useEffect } from "react";
import type { Timeout } from "node:timers";

interface TimeTrackerProps {
  setTimeTracked: (time: string) => void;
}

const TimeTracker: React.FC<TimeTrackerProps> = ({ setTimeTracked }) => {
  const [tracking, setTracking] = useState(false);
  const [clockInTime, setClockInTime] = useState<Date | null>(null);
  const [elapsedTime, setElapsedTime] = useState(0);
  const [location, setLocation] = useState<{ lat: number; lon: number } | null>(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);
  const [dailyTime, setDailyTime] = useState<number>(0); // in seconds

  // Helper to format seconds into "xh ym zs"
  const formatElapsedTime = (seconds: number): string => {
    const hours = Math.floor(seconds / 3600);
    const minutes = Math.floor((seconds % 3600) / 60);
    const secs = seconds % 60;
    return `${hours}h ${minutes}m ${secs}s`;
  };

  // Load username from localStorage on mount
  useEffect(() => {
    const userDetailsStr = localStorage.getItem("userDetails");
    if (userDetailsStr) {
      const { username } = JSON.parse(userDetailsStr);
      setUsername(username);
    }
  }, []);

  // Load the accumulated daily time when username becomes available.
  useEffect(() => {
    if (username) {
      const todayDate = new Date().toISOString().split("T")[0];
      const storedDailyTime = localStorage.getItem(`dailyTime_${username}_${todayDate}`);
      if (storedDailyTime) {
        setDailyTime(parseInt(storedDailyTime));
      } else {
        setDailyTime(0);
      }
      // Update the dashboard (if not currently tracking)
      if (!tracking) {
        setTimeTracked(formatElapsedTime(parseInt(storedDailyTime || "0")));
      }
    }
  }, [username, tracking, setTimeTracked]);

  // Check for saved tracking info. If the stored record is from a previous day, auto clock-out.
  useEffect(() => {
    if (!username) return;
    const storedTrackingInfo = localStorage.getItem(`tracking_${username}`);
    if (storedTrackingInfo) {
      const { clockInTime: storedClockIn, date: storedDate } = JSON.parse(storedTrackingInfo);
      const todayDate = new Date().toISOString().split("T")[0];
      if (storedDate !== todayDate) {
        // Auto clock-out for a previous day's session.
        const storedClockInDate = new Date(storedClockIn);
        // Calculate midnight (12 AM) for the stored day.
        const midnight = new Date(storedClockInDate);
        midnight.setHours(24, 0, 0, 0);
        const sessionSeconds = Math.floor((midnight.getTime() - storedClockInDate.getTime()) / 1000);
        // Update the daily total for the stored day.
        const prevDailyTime = parseInt(localStorage.getItem(`dailyTime_${username}_${storedDate}`) || "0");
        const newDailyTime = prevDailyTime + sessionSeconds;
        localStorage.setItem(`dailyTime_${username}_${storedDate}`, newDailyTime.toString());
        console.log(
          `Auto clocked out previous session on ${storedDate} at midnight with duration: ${sessionSeconds} seconds.`
        );
        // Clear the stale tracking info.
        localStorage.removeItem(`tracking_${username}`);
      } else {
        // If the stored record is from today, resume tracking.
        const clockInDate = new Date(storedClockIn);
        setClockInTime(clockInDate);
        setTracking(true);
        setElapsedTime(Math.floor((Date.now() - clockInDate.getTime()) / 1000));
      }
    }
  }, [username]);

  // Update elapsed time (and dashboard total) every second when tracking.
  useEffect(() => {
    let interval: Timeout | null = null;
    if (tracking && clockInTime) {
      interval = setInterval(() => {
        const sessionSeconds = Math.floor((Date.now() - clockInTime.getTime()) / 1000);
        setElapsedTime(sessionSeconds);
        // Total time = previous sessions (dailyTime) + current session time
        const totalSeconds = dailyTime + sessionSeconds;
        setTimeTracked(formatElapsedTime(totalSeconds));
      }, 1000);
    } else {
      // If not tracking, display only the stored daily total.
      setTimeTracked(formatElapsedTime(dailyTime));
      setElapsedTime(0);
    }
    return () => {
      if (interval) clearInterval(interval);
    };
  }, [tracking, clockInTime, dailyTime, setTimeTracked]);

  // Clear error messages after 3 seconds.
  useEffect(() => {
    if (errorMessage) {
      const timer = setTimeout(() => setErrorMessage(null), 3000);
      return () => clearTimeout(timer);
    }
  }, [errorMessage]);

  // Auto clock-out at midnight if tracking (only works if the tab remains open).
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

  // Fetch the current location using the browser's geolocation.
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

      // Save tracking info (with today's date) so it persists on reload.
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

      if (clockInTime) {
        // Calculate seconds elapsed for this session.
        const sessionSeconds = Math.floor((Date.now() - clockInTime.getTime()) / 1000);
        const todayDate = new Date().toISOString().split("T")[0];
        const newDailyTime = dailyTime + sessionSeconds;
        setDailyTime(newDailyTime);
        localStorage.setItem(`dailyTime_${username}_${todayDate}`, newDailyTime.toString());
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
