import React from 'react';

interface DashboardCardProps {
  title: string;
  icon: React.ElementType;
  value: string;
  className?: string;
}

const DashboardCard: React.FC<DashboardCardProps> = ({ title, icon: Icon, value, className }) => {
  return (
    <div className={`dark:bg-gray-800 bg-white rounded-lg shadow-md p-4 sm:p-6 ${className}`}>
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm font-medium text-gray-600">{title}</p>
          <p className="mt-2 text-2xl sm:text-3xl font-semibold">{value}</p>
        </div>
        <div className="p-2 sm:p-3 bg-gray-100 rounded-full">
          <Icon className="w-5 h-5 sm:w-6 sm:h-6 text-gray-600" />
        </div>
      </div>
    </div>
  );
};

export default DashboardCard;
