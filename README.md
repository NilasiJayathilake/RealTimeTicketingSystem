
# Ticket Ghost : Real-Time Event Ticketing System

A comprehensive and scalable real-time event management and ticketing system that integrates a command-line interface (CLI) with a full-stack web application. The system is designed to manage multiple events simultaneously, supporting vendors and customers for seamless ticket selling and purchasing.

## Features
🔹 General Features

- Multi-Event Support: Host multiple events concurrently.
- Multi-Vendor and Multi-Customer Support: Vendors can sell tickets and customers can purchase them in real time.
- Real-Time Simulations: Simulates ticketing scenarios for each event, showcasing system capabilities.
- Multi-Threading: Java-based multithreading ensures efficient handling of concurrent operations.
- CRUD Operations: Fully supports Create, Read, Update, and - Delete operations for events, vendors, and system configurations.

🔹 Full-Stack Application Features

🌐 Admin Dashboard (Frontend - React.js)

+ View and manage events, vendors, and system configurations.
- Add new events and vendors with a user-friendly interface.
- Run simulations for ticketing events directly from the dashboard.
- Real-time progress updates via WebSockets (planned).

🌐 Backend (Spring Boot)

- Handles business logic and real-time simulations.
- Provides REST APIs for frontend communication.
- Manages data persistence with MySQL.
- Logs system activities in a text file for debugging and audit purposes.
- Configuration management using JSON files for flexible and dynamic settings.
- Command-Line Interface (CLI)
- Lightweight and efficient tool for managing events and running simulations.
- Suitable for quick operations and testing.

## Technologies Used

|Component |Technology|
|----------|----------|
|Backend	|Spring Boot, Java|
|Frontend	|React.js|
|Database	|MySQL|
|Real-Time Updates	|WebSockets (Planned)|
|Logging	|Text File (.txt)|
|Configuration|	JSON File|
|Multithreading	|Java Threads|

## Directory Structure 📂
```
Real-Time-Event-System/  
├── CLI/  
│   ├── Main.java  
│   ├── Utils/  
│   └── Logs/  
├── Full-Stack-Project/  
│   ├── Backend/  
│   │   ├── src/  
│   │   ├── resources/  
│   │   ├── build.gradle  
│   │   └── Logs/  
│   ├── Frontend/  
│   │   ├── src/  
│   │   ├── public/  
│   │   └── node_modules/  
│   └── Configurations/  
└── README.md  
```

## Installation and Setup 🖥️
### Prerequisites
+ Java (JDK 11 or higher)
+ Node.js (v16 or higher)
+ MySQL Server
+ Gradle (for backend)
+ Postman (for API testing)

### Backend Setup
Navigate to the backend directory:

`cd Full-Stack-Project/Backend  `

Update the application.properties file with your MySQL configuration.

Build and run the backend using Gradle:
```
./gradlew clean build  
java -jar build/libs/your-backend-jar-file.jar  
```
Create a MySQL database and ensure the application connects successfully.

### Frontend Setup

Navigate to the frontend directory:
`cd Full-Stack-Project/Frontend  `

Install dependencies:
`npm install  `

Start the frontend:
`npm start  `

### CLI Setup
Compile the CLI application:
`javac CLI/Main.java  `

Run the CLI application:
`java CLI.Main  `

### Running API Calls via Postman 📬
Since the frontend is not fully implemented, follow these steps to configure the system and run simulations via Postman:

Note: Ensure the backend is running before making API calls.

#### Add an Event
URL: `http://localhost:8080/event/addEvent`

Method: POST

JSON Body:
```
{  
  "eventName": "Sample Event",  
  "location": "Any Location",  
  "eventDate": "2023-11-22",  
  "eventTime": "2023-11-22T13:37:00",  
  "noOfTickets": 100,  
  "noOfVendors": 2  
}  
```

Add Multiple Vendors

URL:` http://localhost:8080/vendors/addVendors`

Method: POST

JSON Body:
```
[  
  { "vendorName": "Food Fiesta", "vendorEmail": "foodfiesta@example.com" },  
  { "vendorName": "Tech Gadgets", "vendorEmail": "techgadgets@example.com" },  
  { "vendorName": "Fashion Corner", "vendorEmail": "fashioncorner@example.com" },  
  { "vendorName": "Bookworm's Paradise", "vendorEmail": "bookwormsparadise@example.com" },  
  { "vendorName": "Art & Craft Bazaar", "vendorEmail": "artcraftbazaar@example.com" }  
]  
```

Assign Vendors to an Event

URL: `http://localhost:8080/event/{eventId}/assignVendors`

Method: POST

JSON Body:

```
{  
  "vendorId1": noOfTickets,  
  "vendorId2": noOfTickets  
}  
```

Save Configuration

URL:` http://localhost:8080/config/saveConfig`

Method: POST

JSON Body:
```
{  
  "maxTicketCapacity": 20,  
  "ticketRate": 1000,  
  "ticketReleaseRate": 4,  
  "retrievalRate": 4  
} 
``` 
Start a Simulation

URL: `http://localhost:8080/simulation/start/{eventId}`

Method: POST

## Future Enhancements 🛠️

- Real-time updates for simulations using WebSockets.
- Improved UI for better user experience in the admin dashboard.

## Contribution 🤝
Contributions are welcome! Feel free to fork the repository and create a pull request with your proposed changes.

## License 📄
This project is licensed under the MIT License.
