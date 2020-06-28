package eg.edu.alexu.csd.oop.calculator.cs32;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {
    private Calculator op = new Calc();
    @FXML
    TextField text;
    @FXML
    Label l;
    @FXML
    Button prev, next;

    @FXML
    private void addToTextField(MouseEvent event) {
        if (!l.getText().equals("")) {
            clear();
        }
        Button mybutton = (Button) event.getSource();
        text.setText(text.getText() + mybutton.getText());
    }

    @FXML
    private void exit() {
        Platform.exit();
    }

    @FXML
    private void calculate() {
        op.input(text.getText());
        try {
            l.setText(op.getResult());

        } catch (RuntimeException e) {
            showError(e.getMessage());
            clear();
            return;
        }
        enable();

    }

    @FXML
    private void getPrev() {
        l.setText("");
        text.setText(op.prev());
        enable();

    }

    @FXML
    private void getNext() {
        l.setText("");
        text.setText(op.next());
        enable();
    }

    @FXML
    private void save() {
        op.save();
    }

    @FXML
    private void load() {
        op.load();
        enable();
    }

    private void showError(String str) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error!");
        errorAlert.setHeaderText("Look down for details");
        errorAlert.setContentText(str);
        errorAlert.showAndWait();
    }

    @FXML
    private void clear() {
        text.setText("");
        l.setText("");
    }

    private void enable() {

        if (((Calc) op).getI() == ((Calc) op).getIndex() - 1) {
            next.setDisable(true);
        } else {
            next.setDisable(false);
        }
        if (((Calc) op).getI() == 0) {
            prev.setDisable(true);
        } else {
            prev.setDisable(false);

        }

    }
}
