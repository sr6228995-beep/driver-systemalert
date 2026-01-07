**🚗 Driver Alert System Using OpenCV (Java) 📌 Project Overview**

The Driver Alert System is a real-time computer vision application designed to monitor driver alertness and detect signs of drowsiness. Using a webcam and OpenCV’s face and eye detection techniques, the system identifies prolonged eye closure and triggers an alert to prevent fatigue-related accidents.

This project demonstrates the practical use of image processing, real-time video analysis, and Haar Cascade classifiers to enhance road safety. 🎯 Objectives

Detect the driver’s face and eyes in real time

Monitor eye closure across consecutive frames

Identify possible drowsiness conditions

Trigger alerts to warn the driver

Demonstrate OpenCV integration with Java

🛠️ Technologies Used

Java (JDK 8 / 17)

OpenCV 4.x

Haar Cascade Classifiers

Webcam (Video Input)

**🧠 Working Principle**

The webcam captures live video frames.

Each frame is converted to grayscale for efficient processing.

Face detection is performed using haarcascade_frontalface_default.xml.

Eye detection is performed inside the detected face region using haarcascade_eye.xml.

If eyes are not detected continuously for a defined number of frames, the system assumes drowsiness.

An alert is triggered and displayed to warn the driver.

**⚙️ Features**

Real-time face and eye detection

Frame-based drowsiness detection

Visual feedback using rectangles and circles

Console-based alert system

Lightweight and fast execution

**🔧 Setup Instructions 1️⃣ Download OpenCV**

Download OpenCV from:

https://opencv.org/releases/

Extract the files.

2️⃣ Add OpenCV to Project

Add this JAR file to your project:

opencv/build/java/opencv-4xx.jar

3️⃣ Add Native Library

Set VM arguments:

-Djava.library.path="opencv/build/java/x64"

4️⃣ Verify Resource Paths

Ensure cascade files are located correctly:

src/resources/haarcascade_frontalface_default.xml src/resources/haarcascade_eye.xml

▶️ How to Run

Connect a webcam

Run DriverAlertSystem.java

Press ESC to stop monitoring

🚨 Alert Mechanism

If eyes are not detected for 10 consecutive frames, the system triggers:

⚠ ALERT! Possible driver drowsiness detected!
