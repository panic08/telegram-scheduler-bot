<h1>Telegram-Scheduler-Bot</h1>
<h2>To install you will need:</h2>

- *Java 17 (JRE 17)*
- *POSTGRESQL 15*
- *INTERNET CONNECTION*

<h3>1. Installing missing components</h3>Install JRE 17 (we will do it on Ubuntu 22.02 LTS)
<br/>
To do this, open a terminal and enter commands:

- **sudo apt update**
- **sudo apt install default-17-jre**

We can check the java version with a command:

- **java -version**

Next, install the database Postregsql 15 from the off repository.
<br/>
To do this, let's enter commands:
<br/>
- **sudo apt install postgresql postgresql-contrib**
- **sudo systemctl start postgresql.service**

Next, simply create a database
<h3>2. Set up app.properties</h3>
Go to application.properties, and enter our data from the database, as well as the token from telegram, 
which we can get: [BotFather](https://t.me/BotFather)
<br/>
Now we need to replace all values with our own (database name, password, URL, telegram token, and so on).
<h3>3. Launching an application</h3>
Build and run the application with the command:

- **mvn package**
- **java -jar lapayment-0.0.1-SNAPSHOT.jar**

