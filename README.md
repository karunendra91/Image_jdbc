# Image JDBC - Java Image Storage and Retrieval System

## 📋 Project Overview

This project demonstrates how to **store images in a MySQL database** and **retrieve them back to the file system** using **JDBC (Java Database Connectivity)** in Java. It provides functionality for managing binary image data through SQL queries.

---

## 🏗️ Project Structure

```
Image_jdbc/
├── src/
│   └── Main.java          # Main application class
├── IMG_JDBC.iml           # IntelliJ IDEA project configuration
├── .gitignore             # Git ignore rules
└── README.md              # This file
```

---

## 📝 Main Java File Explanation: `Main.java`

### **1. Imports**
```java
import java.io.FileInputStream;      // Read image from file
import java.io.FileOutputStream;     // Write image to file
import java.io.OutputStream;         // Output stream operations
import java.sql.Connection;          // Database connection
import java.sql.DriverManager;       // Manage JDBC connections
import java.sql.PreparedStatement;   // Execute parameterized SQL queries
import java.sql.ResultSet;           // Retrieve query results
```

---

### **2. Configuration & Setup**

#### **Database Connection Details**
```java
String url = "jdbc:mysql://localhost:3306/mydatabase";
String username = "root";
String password = "root";
```
- **URL**: Connects to MySQL database named `mydatabase` on localhost
- **Username/Password**: MySQL credentials (root/root)

#### **File Paths**
```java
String image_path = "E:\\image\\img1.jpg";        // Source image to upload
String folder_path = "E:\\image\\";               // Folder to save extracted images
```

#### **SQL Queries**
```java
String INSERT_DB = "INSERT INTO image_table(image_data) VALUES(?)";
String INSERT_FILE = "select image_data from image_table where image_id = (?)";
```
- **INSERT_DB**: Insert image into database
- **INSERT_FILE**: Retrieve image from database by ID

---

### **3. Workflow Explanation**

#### **Step 1: Load JDBC Driver**
```java
Class.forName("com.mysql.cj.jdbc.Driver");
System.out.println("Driver loaded Successfully....");
```
- Loads the MySQL JDBC driver
- This must be done before any database operations
- Uses `com.mysql.cj.jdbc.Driver` for MySQL Connector/J

---

#### **Step 2: Insert Image to Database (Commented - Optional)**
```java
/*
try {
    // Establish connection
    Connection connection = DriverManager.getConnection(url, username, password);
    
    // Read image file into byte array
    FileInputStream fis = new FileInputStream(image_path);
    byte[] image = new byte[fis.available()];
    fis.read(image);
    
    // Prepare and execute INSERT statement
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DB);
    preparedStatement.setBytes(1, image);           // Set binary image data
    int res = preparedStatement.executeUpdate();
    
    if (res > 0) {
        System.out.println("Insertion success...");
    } else {
        System.out.println("Insertion failed...");
    }
    
    // Clean up resources
    fis.close();
    preparedStatement.close();
    connection.close();
} catch (Exception e) {
    System.out.println(e.getMessage());
}
*/
```

**What it does:**
1. Opens database connection
2. Reads image file into memory as byte array
3. Creates `PreparedStatement` with parameterized query
4. Binds binary image data to the query
5. Executes INSERT and reports success/failure
6. Closes all resources

---

#### **Step 3: Retrieve Image from Database (Active)**
```java
try {
    // Establish connection
    Connection connection = DriverManager.getConnection(url, username, password);
    System.out.println("Database connected Successfully....");
    
    // Prepare SELECT statement
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILE);
    preparedStatement.setInt(1, 1);                 // Query by image_id = 1
    
    // Execute query and get results
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if (resultSet.next()) {
        // Get binary image data from database
        byte[] data = resultSet.getBytes("image_data");
        
        // Define output file path
        String image_pat = folder_path + "extracted_img.jpg";
        
        // Write image to file system
        OutputStream outputStream = new FileOutputStream(image_pat);
        outputStream.write(data);
        System.out.println("Image extracted successfully...");
    } else {
        System.out.println("image not found...");
    }
    
    // Clean up resources
    preparedStatement.close();
    connection.close();
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

**What it does:**
1. Connects to the database
2. Retrieves image data by `image_id = 1`
3. Checks if image exists in ResultSet
4. Converts binary data to file on disk
5. Saves extracted image to specified folder
6. Handles errors gracefully

---

## 🔄 How Everything Works Together

```
┌─────────────────────────────────────────┐
│  1. Load MySQL JDBC Driver              │
└────────────┬────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────┐
│  2. Connect to MySQL Database           │
│     (jdbc:mysql://localhost:3306/...)   │
└────────────┬────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────┐
│  3. Execute SQL Query (SELECT)          │
│     Get image by ID from database       │
└────────────┬────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────┐
│  4. Retrieve Binary Data (ResultSet)    │
│     Extract byte[] from database        │
└────────────┬────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────┐
│  5. Write to File System                │
│     Save byte[] to disk as JPG          │
└────────────┬────────────────────────────┘
             │
             ↓
┌─────────────────────────────────────────┐
│  6. Close Resources                     │
│     (Connection, Statement, Stream)     │
└─────────────────────────────────────────┘
```

---

## 📋 Prerequisites & Setup

### **Required Software**
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL JDBC Driver (mysql-connector-java)
- IntelliJ IDEA (optional, but project is configured for it)

### **Database Setup**
```sql
-- Create database
CREATE DATABASE mydatabase;

-- Use database
USE mydatabase;

-- Create image table
CREATE TABLE image_table (
    image_id INT AUTO_INCREMENT PRIMARY KEY,
    image_data LONGBLOB NOT NULL
);
```

### **Add MySQL JDBC Driver**
1. Download `mysql-connector-java-*.jar` from [MySQL website](https://dev.mysql.com/downloads/connector/j/)
2. Add to project classpath/lib folder
3. Configure in IntelliJ: File → Project Structure → Libraries

---

## ⚙️ Configuration Steps

### **Step 1: Update Database Credentials**
Edit `Main.java` line 13-15:
```java
String url = "jdbc:mysql://localhost:3306/YOUR_DATABASE";
String username = "YOUR_USERNAME";
String password = "YOUR_PASSWORD";
```

### **Step 2: Update File Paths**
Edit `Main.java` line 16-18:
```java
String image_path = "YOUR_LOCAL_IMAGE_PATH";      // Source image
String folder_path = "YOUR_OUTPUT_FOLDER_PATH";   // Output folder
```

### **Step 3: Toggle Features**
- **Uncomment lines 34-58** to enable image insertion
- **Keep lines 61-82 active** for image retrieval

---

## 🚀 Running the Program

### **Compile**
```bash
javac src/Main.java
```

### **Run**
```bash
java -cp src:lib/mysql-connector-java-*.jar Main
```

### **Expected Output**
```
Driver loaded Successfully....
Database connected Successfully....
Image extracted successfully...
```

---

## 🔑 Key Concepts Explained

### **1. PreparedStatement**
- Prevents SQL injection attacks
- Allows parameterized queries with `?` placeholders
- Binds values safely using `setBytes()`, `setInt()`, etc.

### **2. Binary Data Handling**
- Images stored as `LONGBLOB` in MySQL
- Read from disk: `FileInputStream` → `byte[]`
- Write to disk: `byte[]` → `FileOutputStream`
- Transfer to DB: `preparedStatement.setBytes()`

### **3. Resource Management**
```java
try {
    // Operations
} catch (Exception e) {
    // Error handling
} finally {
    // Close resources (Connection, Statement, Streams)
}
```

### **4. JDBC Connection Pool**
- Each operation creates new connection
- For production, use connection pooling (HikariCP, C3P0)

---

## 📌 Common Issues & Solutions

| Issue | Cause | Solution |
|-------|-------|----------|
| `ClassNotFoundException` | MySQL driver not in classpath | Add mysql-connector-java JAR to project |
| `SQLException: Connection refused` | MySQL server not running | Start MySQL service |
| `SQLException: Unknown database` | Database doesn't exist | Create database using provided SQL script |
| `IOException: File not found` | Wrong file path | Update `image_path` and `folder_path` |
| `NullPointerException` | No image found in database | Insert image first using commented code |

---

## 🎯 Features & Capabilities

✅ **Load JDBC MySQL Driver**  
✅ **Establish Database Connection**  
✅ **Insert Binary Image Data** (commented)  
✅ **Retrieve Image by ID**  
✅ **Save Image to File System**  
✅ **Error Handling & Logging**  
✅ **Resource Cleanup**  

---

## 🔮 Future Enhancements

- [ ] Add connection pooling
- [ ] Support multiple image formats
- [ ] Implement image compression
- [ ] Add batch insert operations
- [ ] Create GUI interface
- [ ] Add metadata (filename, upload date, etc.)
- [ ] Implement image search functionality
- [ ] Add logging framework (Log4j, SLF4J)

---

## 📚 References

- [MySQL JDBC Documentation](https://dev.mysql.com/doc/connector-j/en/)
- [Java JDBC API Docs](https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html)
- [MySQL BLOB Storage](https://dev.mysql.com/doc/refman/8.0/en/blob.html)

---

## 📄 License

This project is open source and available for educational purposes.

---

## 👤 Author

**Karunendra**

---

## ❓ Questions or Issues?

Feel free to open an issue or contribute improvements to this project!
