# Anime Tracker
![Scala](https://img.shields.io/badge/scala-%23DC322F.svg?style=for-the-badge&logo=scala&logoColor=white)

Anime Tracker is a basic API wrapper written in Scala for the official MyAnimeList API. The link to the official API [can be found here](https://myanimelist.net/apiconfig/references/api/v2).

It is intended to be used as code for a fully fledged web application. A very basic example of such an application has been provided to demo how Anime Tracker can be incorporated into your project. 

Anime Tracker was designed as a practice exercise to gain experience in using the Scala programming language.

### **How do I use Anime Tracker?**
---

Anime Tracker has two core endpoints. The first of these relates to the user, and the second relates to weekly episode releases. Please note that in order to use Anime Tracker with your project, you will need to supply your own MyAnimeList API key. Once acquired, it needs to be placed in a .env file at the root of the "backend" directory with the label "MAL_CLIENT_ID"
### **1. User Endpoint**

The user endpoint has the following format:

`http://localhost:8080/user?user_name=toadkarter1993`

Once submitted, Anime Tracker will select all shows the user currently has set as "watching" for the current season and will calculate a timetable for all episodes currently airing. 

A JSON object will be returned containing all of this information, as well as some additional userful information like such as links to the show poster. 

The user information will also be cached in the backend to make sure that any subsequen requests are significantly faster. 

### **2. Episodes Endpoint**

The episodes endpoint has the following format: 

`http://localhost:8080/episodes`

Once submitted Anime Tracker will provide a list of all dates in the coming week that will have episodes of shows the user is currently watching.

### **What technologies does Anime Tracker use?**
---

### **1. Scala**

The ultimate goal of this project was to gain familiarity with Scala, which is why this language was used. 
### **2. Akka-Http**

Akka-http was used to quickly create an HTTP server. Other frameworks like Play were considered but ultimately not chosen due to their complexity, which this project did not warrant. 

### **3. ujson**

This simple library was used to serialize and deserialize JSON objects. 

**END**
