import java.io.File;
class soundFile {
    public static void main(String[] args) {
        File soundFile = new File("src/resouces/beep.wav");
        System.out.println(soundFile.getAbsolutePath());
        System.out.println(soundFile.exists());

    }
}
