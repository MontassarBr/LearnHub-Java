# LearnHub - Java Swing Project

![LearnHub Banner](screenshots/banner.png)

## 📌 Overview
**LearnHub** is a desktop application built in **Java Swing** with **MySQL integration**.  
It aims to connect students and trainers by offering:
- User authentication (students, trainers, admins).
- Formation (course) management.
- User-friendly GUI with modern rounded components.
- Role-based features (Students can register, Trainers can create formations, Admins can manage users).

---

## 🚀 Features
- 🔑 **Login & Registration** for Students and Trainers  
- 📚 **Browse Formations** (view ID, title, description, trainer, price)  
- 📝 **Student Registration** for formations  
- 👨‍🏫 **Trainer Tools** to add/manage formations  
- 🛠️ **Admin Panel** to manage users (view & delete users)  
- 🎨 **Custom Styled UI** (rounded buttons & inputs, sidebar navigation)  

---

## 🖼️ Screenshots

### 1️⃣ Login Page
![Screenshot 1](screenshots/Screenshot1.png)

### 2️⃣ Regestration page
![Screenshot 2](screenshots/Screenshot2.png)

### 3️⃣ 
![Screenshot 3](screenshots/Screenshot3.png)

### 4️⃣ Add Formation (Trainer)
![Screenshot 4](screenshots/Screenshot4.png)

### 5️⃣ 
![Screenshot 5](screenshots/Screenshot5.png)

### 6️⃣ 
![Screenshot 6](screenshots/Screenshot6.png)

### 7️⃣ 
![Screenshot 7](screenshots/Screenshot7.png)

---

## 🛠️ Technologies Used
- **Java Swing** – GUI development  
- **MySQL** – Database management  
- **JDBC** – Database connectivity  
- **Java AWT** – Graphics & UI styling  
- **javax.sound.sampled** – Sound support (optional)  

---


## ⚙️ Installation & Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/LearnHub-Java.git
   cd LearnHub-Java


2. Add **MySQL Connector JAR** to your classpath.
3. Create a MySQL database and import your schema.
4. Update DB credentials in the `connectionBD.java` file.
5. Compile and run:

   ```bash
   javac -cp .;lib/mysql-connector-j-8.0.33.jar src/*.java
   java -cp .;lib/mysql-connector-j-8.0.33.jar src.Main
   ```

---

## 👨‍💻 Authors

* **Montassar Braiek**

---

## 📜 License

Montassar Braiek 2024.

