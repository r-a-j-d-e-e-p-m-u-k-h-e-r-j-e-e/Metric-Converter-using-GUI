import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MetricConverter extends Application {
    private ComboBox<String> fromComboBox;
    private ComboBox<String> toComboBox;
    private TextField inputField;
    private Label outputLabel;

    private static final String[] UNITS = {"m", "km", "ft", "mi", "kg", "lb", "kph", "mph"};
    private static final double[][] CONVERSION_RATES = {
        {1, 0.001, 3.28084, 0.621371, 1, 2.20462, 0.621371, 0.621371},
        {1000, 1, 3280.84, 0.001, 1000, 2.20462, 1000, 0.621371},
        {0.3048, 0.0003048, 1, 0.000189394, 0.000204617, 0.000453592, 0.0003048, 0.000189394},
        {1.609344, 0.621371, 5280, 1, 1.609344, 3.527396, 1.609344, 1},
        {0.001, 1000, 0.00110231, 0.000621371, 1, 2.20462, 0.001, 0.000621371},
        {0.453592, 0.000453592, 0.00220462, 0.0005, 0.453592, 1, 0.453592, 0.0005},
        {1, 0.621371, 1.609344, 0.621371, 1, 1.609344, 1, 0.621371},
        {1.609344, 0.621371, 1.09728, 0.621371, 1.609344, 3.527396, 1.609344, 1}
    };

    @Override
    public void start(Stage stage) {
        fromComboBox = new ComboBox<>();
        fromComboBox.getItems().addAll(UNITS);
        fromComboBox.setValue(UNITS[0]);

        toComboBox = new ComboBox<>();
        toComboBox.getItems().addAll(UNITS);
        toComboBox.setValue(UNITS[1]);

        inputField = new TextField();
        inputField.setPromptText("Enter value to convert");

        outputLabel = new Label();
        outputLabel.setPadding(new Insets(10));
        outputLabel.setWrapText(true);

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(event -> convert());

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(new Label("From:"), 0, 0);
        gridPane.add(fromComboBox, 1, 0);
        gridPane.add(new Label("To:"), 0, 1);
        gridPane.add(toComboBox, 1, 1);
        gridPane.add(new Label("Value:"), 0, 2);
        gridPane.add(inputField, 1, 2);
        gridPane.add(convertButton, 0, 3, 2, 1);
        gridPane.add(outputLabel, 0, 4, 2, 1);
        gridPane.setPadding(new Insets(20));
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setTitle("Metric Converter");
        stage.show();
    }
    
    private void convert() {
        String fromUnit = fromComboBox.getValue();
        String toUnit = toComboBox.getValue();
        double inputValue;
    
        try {
            inputValue = Double.parseDouble(inputField.getText());
        } catch (NumberFormatException e) {
            outputLabel.setText("Invalid input value");
            return;
        }
    
        int fromIndex = getIndex(fromUnit);
        int toIndex = getIndex(toUnit);
    
        double conversionFactor = CONVERSION_RATES[fromIndex][toIndex];
        double result = inputValue * conversionFactor;
    
        String outputText = String.format("%.2f %s = %.2f %s", inputValue, fromUnit, result, toUnit);
        outputLabel.setText(outputText);
    }
    
    private int getIndex(String unit) {
        for (int i = 0; i < UNITS.length; i++) {
            if (UNITS[i].equals(unit)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}    