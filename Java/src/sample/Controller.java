package sample;

import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import static org.apache.pdfbox.pdmodel.PDDocument.load;
/**
 * Класс, обрабатывающий события
 */
public class Controller {

    public javafx.scene.control.Label label1;
    public javafx.scene.control.Label label2;
    public javafx.scene.control.Label label3;
    public javafx.scene.control.Label label4;
    public javafx.scene.control.Label label5;
    public RadioButton radioBat1;
    public RadioButton radioBat2;
    public RadioButton radioBat3;
    public RadioButton radioBat4;
    public RadioButton radioBat5;
    public RadioButton radioBat6;
    public ToggleGroup TitleList;
    public ToggleGroup EndList;
    public javafx.scene.control.Button button9;
    public javafx.scene.control.Label label6;
    Button button1 = new Button();
    Button button2 = new Button();
    Button button3 = new Button();
    Button button4 = new Button();
    Button button5 = new Button();
    Button button6 = new Button();
    Button button7 = new Button();
    private Boolean BrochureMaker = false;
    private Boolean TitleFlag = false;
    private Boolean EndDataFlag = false;
    private Boolean StartDataFlag = false;
    private static Stage stage;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    /**
     * Открытие PDF-файла
     * @param actionEvent - событие нажатия на кнопку
     */
    public void OpenFile(ActionEvent actionEvent) {
        final FileChooser fileChooserPDF = new FileChooser();
        fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooserPDF.showOpenDialog(stage);
        if (file != null) {
            Brochure.Split(file.getAbsolutePath());
            TitleFlag = true;
            label3.setText(file.getName() + " добавлен");
        }
    }

    /**
     * Очистка документа
     * @param actionEvent - событие нажатия на кнопку
     */
    public void ClearDocument(ActionEvent actionEvent) {
        if (!TitleFlag) {
            button1.setEnabled(false);
            alert.setTitle("Очистить лист документов");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Открытие файла");
            alert.showAndWait();
        } else {
            Brochure.ClearDocument();
            TitleFlag = false;
            EndDataFlag = false;
            StartDataFlag = false;
            label1.setText("");
            label2.setText("");
            label3.setText("");
            label4.setText("");
            label5.setText("");
            label6.setText("");
            Brochure.ClearDocument();
            BrochureMaker = false;
        }
    }

    /**
     * Добавление титульного листа
     * @param actionEvent - событие нажатия на кнопку
     */
    public void AddStartImage(ActionEvent actionEvent) throws Exception {
        if (!TitleFlag) {
            button1.setEnabled(false);
            alert.setTitle("Добавить титульный лист");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Открытие файла");
            alert.showAndWait();
        } else {
            if (radioBat1.isSelected()) {
                final FileChooser fileChooserPDF = new FileChooser();
                fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                File file = fileChooserPDF.showOpenDialog(stage);
                if (file != null) {
                    try {
                        Brochure.StartPDFImage(file.getAbsolutePath());
                        label2.setText(file.getName() + " добавлен");
                        StartDataFlag = true;
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }
                }
            } else if (radioBat2.isSelected()) {
                final FileChooser fileChooserPDF = new FileChooser();
                final FileChooser fileChooserIMG = new FileChooser();
                fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                fileChooserIMG.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
                File file = fileChooserIMG.showOpenDialog(stage);
                if (file != null) {
                    Brochure.AddStratImage(file.getAbsolutePath());
                    label2.setText(file.getName() + " добавлен");
                    StartDataFlag = true;
                }
            }
        }
    }

    /**
     * Добавление последнего листа
     * @param actionEvent - событие нажатия на кнопку
     */

    public void AddEndImage(ActionEvent actionEvent) throws Exception {
        if (!TitleFlag) {
            button1.setEnabled(false);
            alert.setTitle("Добавить последний лист");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Открытие файла");
            alert.showAndWait();
        } else {
            if (radioBat3.isSelected()) {
                final FileChooser fileChooserPDF = new FileChooser();
                fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                File file = fileChooserPDF.showOpenDialog(stage);
                if (file != null) {
                    try {
                        Brochure.EndPDFImage(file.getAbsolutePath());
                        label1.setText(file.getName() + " добавлен");
                        EndDataFlag = true;
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }
                }
            } else if (radioBat4.isSelected()) {
                final FileChooser fileChooserPDF = new FileChooser();
                final FileChooser fileChooserIMG = new FileChooser();
                fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
                fileChooserIMG.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
                File file = fileChooserIMG.showOpenDialog(stage);
                if (file != null) {
                    Brochure.AddEndImage(file.getAbsolutePath());
                    label1.setText(file.getName() + " добавлен");
                    EndDataFlag = true;
                }
            }
        }
    }

    /**
     * Добавление входных данных
     * @param actionEvent - событие нажатия на кнопку
     */
    public void StartContent(ActionEvent actionEvent) {
        if (!StartDataFlag && !EndDataFlag) {
            button1.setEnabled(false);
            alert.setTitle("Добавить входные данные");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Добавить титульный лист и Добавить последний лист");
            alert.showAndWait();
        } else {
            final FileChooser fileChooserPDF = new FileChooser();
            fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooserPDF.showOpenDialog(stage);
            if (file != null) {
                try {
                    Brochure.StartContent(file.getAbsolutePath());
                    label4.setText(file.getName() + " добавлен");
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Добавление выходных данных
     * @param actionEvent - событие нажатия на кнопку
     */

    public void EndContent(ActionEvent actionEvent) {
        if (!StartDataFlag && !EndDataFlag) {
            button1.setEnabled(false);
            alert.setTitle("Добавить выходные данные");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Добавить титульный лист и Добавить последний лист");
            alert.showAndWait();
        } else {
            final FileChooser fileChooserPDF = new FileChooser();
            fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooserPDF.showOpenDialog(stage);
            if (file != null) {
                try {
                    Brochure.EndContent(file.getAbsolutePath());
                    label5.setText(file.getName() + " добавлен");
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Печать документа
     * @param actionEvent - событие нажатия на кнопку
     */

    public void PrintDocument(ActionEvent actionEvent) throws IOException, PrinterException {
        if (!BrochureMaker) {
            button1.setEnabled(false);
            alert.setTitle("Печать");
            alert.setHeaderText(null);
            alert.setContentText("Выполните пункт Создание брошюры");
            alert.showAndWait();
        } else {
            final FileChooser fileChooserPDF = new FileChooser();
            fileChooserPDF.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            File file = fileChooserPDF.showOpenDialog(stage);
            if (file != null) {
                Brochure.printPDF(load(file));
            }
        }
    }

    /**
     * Расположение страниц в брошюре
     * @param actionEvent - событие нажатия на кнопку
     */

    public void MergePDF(ActionEvent actionEvent) {
        if (radioBat5.isSelected()) {
            if (!TitleFlag) {
                button1.setEnabled(false);
                alert.setTitle("Создание брошюры");
                alert.setHeaderText(null);
                alert.setContentText("Выполните пункт Открытие файла");
                alert.showAndWait();
            } else {
                try {

                    Brochure.manipulatePdf(Brochure.path);
                    BrochureMaker = true;
                    //button6.setEnabled(false);
                    alert.setTitle("Создание брошюры");
                    alert.setHeaderText(null);
                    alert.setContentText("Брошюра создана");
                    alert.showAndWait();
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        } else if (radioBat6.isSelected()) {
            if (!TitleFlag) {
                button1.setEnabled(false);
                alert.setTitle("Создание брошюры");
                alert.setHeaderText(null);
                alert.setContentText("Выполните пункт Открытие файла");
                alert.showAndWait();
            } else {
                try {
                    Brochure.manipulatePdf(Brochure.path);
                    Brochure.GetReadyToPrint();
                    BrochureMaker = true;
                    //button6.setEnabled(false);
                    alert.setTitle("Создание брошюры");
                    alert.setHeaderText(null);
                    alert.setContentText("Брошюра создана");
                    alert.showAndWait();
                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
