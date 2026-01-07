import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class DriveTest {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private static int closedEyeFrames = 0;
    private static final int DROWSY_THRESHOLD = 10;

    public static void main(String[] args) {

        String faceCascadePath = "src/resouces/haarcascade_frontalface_default.xml";
        String eyeCascadePath = "src/resouces/haarcascade_eye.xml";

        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);
        CascadeClassifier eyeCascade = new CascadeClassifier(eyeCascadePath);

        if (faceCascade.empty() || eyeCascade.empty()) {
            System.err.println("Error: Could not load cascade files. Check paths!");
            return;
        }

        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.err.println("Error: Cannot open webcam.");
            return;
        }

        Mat frame = new Mat();
        System.out.println("=== Driver Alert System started ===");

        while (true) {
            if (!camera.read(frame)) {
                System.err.println("Warning: No frame captured.");
                break;
            }

            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            MatOfRect faces = new MatOfRect();
            faceCascade.detectMultiScale(gray, faces, 1.1, 5, 0, new Size(100, 100), new Size());

            for (Rect face : faces.toArray()) {
                Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 2);

                Mat faceROI = gray.submat(face);
                MatOfRect eyes = new MatOfRect();
                eyeCascade.detectMultiScale(faceROI, eyes, 1.1, 5, 0, new Size(20, 20), new Size());

                for (Rect eye : eyes.toArray()) {
                    Point eyeCenter = new Point(face.x + eye.x + eye.width / 2.0, face.y + eye.y + eye.height / 2.0);
                    int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                    Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 2);
                }

                if (eyes.toArray().length == 0) {
                    closedEyeFrames++;
                    System.out.println("Eyes not detected (" + closedEyeFrames + " frames)");
                    if (closedEyeFrames >= DROWSY_THRESHOLD) {
                        triggerAlert();
                        playBeep();  // Play MP3 alert
                        closedEyeFrames = 0;
                    }
                } else {
                    closedEyeFrames = 0;
                    System.out.println("Driver is attentive ✅ Eyes detected: " + eyes.toArray().length);
                }

                break; // Only check first face
            }

            HighGui.imshow("Driver Monitor", frame);
            if (HighGui.waitKey(1) == 27) { // ESC to exit
                break;
            }
        }

        camera.release();
        HighGui.destroyAllWindows();
        System.out.println("Monitoring ended.");
    }

    private static void triggerAlert() {
        System.out.println("⚠ ALERT! Possible driver drowsiness detected!");
        System.out.println("Beep! Beep! 🚨 Take a break.\n");
    }

    // Play MP3 alert using JLayer
    private static void playBeep() {
        new Thread(() -> {
            try (FileInputStream fis = new FileInputStream("src/resouces/beep-125033.mp3")) {
                Player player = new Player(fis);
                player.play();
            } catch (Exception e) {
                System.err.println("Error playing MP3: " + e.getMessage());
            }
        }).start();
    }
}
