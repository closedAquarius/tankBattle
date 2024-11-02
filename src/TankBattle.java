import com.csu.tankbattle.Manager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TankBattle extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Manager.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
