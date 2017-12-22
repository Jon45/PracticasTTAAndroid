public class Business implements BusinessInterface {
    @Override
    public boolean login(String userName, String Password) {
        return true;
    }

    @Override
    public TestTTA getNewTest() {
        return null;
    }

    @Override
    public String getNewExercise() {
        return null;
    }

    @Override
    public boolean uploadFile() {
        return false;
    }

    @Override
    public boolean takePhoto() {
        return false;
    }

    @Override
    public boolean recordAudio() {
        return false;
    }

    @Override
    public boolean recordVideo() {
        return false;
    }
}
