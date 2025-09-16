import javafx.application.Application;
import javafx.stage.Stage;
import view.ProductFormView;
import controller.ProductFormController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ProductFormView view = new ProductFormView();
        new ProductFormController(view);
        view.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}