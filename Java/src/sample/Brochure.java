package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с PDF-документом
 */
public class Brochure {

    private static Stage stage;
    private static List<InputStream> list = new ArrayList<InputStream>();
    private static OutputStream out;
    private static FileChooser fileChooser = new FileChooser();
    public static String path;

    static {
        try {
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
            path = String.valueOf(fileChooser.showSaveDialog(stage));
            out = new FileOutputStream(new File(path));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет выбранный пользователем файл в лист
     * @param inFile - путь к добавляемому файлу
     */
    static void Split(String inFile) {
        try {
            System.out.println("Чтение " + inFile);
            PdfReader reader = new PdfReader(inFile);
            int n = reader.getNumberOfPages();
            System.out.println("Количество страниц : " + n);
            int i = 0;
            while (i < n) {
                String outFile = inFile.substring(0, inFile.indexOf(".pdf")) + "-" + String.format("%03d", i + 1) + ".pdf";
                System.out.println("Запись " + outFile);
                Document document = new Document(reader.getPageSizeWithRotation(1));
                PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                document.open();
                PdfImportedPage page = writer.getImportedPage(reader, ++i);
                writer.addPage(page);
                Brochure.list.add(new FileInputStream(new File(outFile)));
                document.close();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавляет титульный лист в формате .JPG
     * @param inFile - путь к титульному листу в формате .JPG
     */
    public static void AddStratImage(String inFile) throws Exception {
        Document document = new Document(PageSize.A4);
        String dest = inFile.substring(0, inFile.indexOf(".jpg")) + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Image stamp = Image.getInstance(inFile);
        stamp.scalePercent(71.7f);
        stamp.setAbsolutePosition(0, 0);
        stamp.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        document.add(stamp);
        Brochure.list.add(new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     * Добавляет последний лист в формате .JPG
     * @param inFile - путь к последнему листу в формате .JPG
     */
    public static void AddEndImage(String inFile) throws Exception {
        Document document = new Document(PageSize.A4);
        String dest = inFile.substring(0, inFile.indexOf(".jpg")) + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        Image stamp = Image.getInstance(inFile);
        stamp.scalePercent(71.7f);
        stamp.setAbsolutePosition(0, 0);
        stamp.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        document.add(stamp);
        Brochure.list.add(0, new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     * Добавляет входные данные в формате .PDF
     * @param infile - путь к документу входных данных
     */
    public static void StartContent(String infile) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        String dest = infile.substring(0, infile.indexOf(".pdf")) + "-" + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfReader reader = new PdfReader(infile);
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);
        Brochure.list.add(1, new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     *  Добавляет вsходные данные в формате .PDF
     * @param infile - путь к документу выодных данных
     */
    public static void EndContent(String infile) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        String dest = infile.substring(0, infile.indexOf(".pdf")) + "-" + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfReader reader = new PdfReader(infile);
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);
        int k = list.size();
        Brochure.list.add(k - 1, new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     * Добавляет титульный лист в формате .PDF
     * @param infile - путь к титульному листу в формате PDF
     */
    public static void StartPDFImage(String infile) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        String dest = infile.substring(0, infile.indexOf(".pdf")) + "-" + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfReader reader = new PdfReader(infile);
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);
        Brochure.list.add(new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     * Добавляет последний лист в формате .PDF
     * @param infile - путь к последнему листу в формате PDF
     */
    public static void EndPDFImage(String infile) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        String dest = infile.substring(0, infile.indexOf(".pdf")) + "-" + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfReader reader = new PdfReader(infile);
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        document.newPage();
        cb.addTemplate(page, 0, 0);
        Brochure.list.add(0, new FileInputStream(new File(dest)));
        document.close();
    }
    /**
     * Объединение документов из листа в один документ
     * @param  list - Входной лист документов, который собрал пользователь
     * @param  outputStream - путь, куда будет помощен совмещенный лист документов
     */
    public static void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage page = writer.getImportedPage(reader, i);
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }
    /**
     * Расположение страниц в формате брошюры
     * @param src - путь к входному файлу
     */
    public static void manipulatePdf(String src) throws IOException, DocumentException {
        doMerge(list, out);
        // Creating a reader
        PdfReader reader = new PdfReader(src);
        // step 1
        Document document = new Document(PageSize.A3.rotate());
        // step 2
        String dest = src.substring(0, src.indexOf(".pdf")) + "Temp" + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        // step 3
        document.open();
        // step 4
        PdfContentByte canvas = writer.getDirectContent();
        float a4_width = PageSize.A4.getWidth();
        int n = reader.getNumberOfPages();
        int p = 1;
        PdfImportedPage page;
        if (n % 2 == 1) {
            page = writer.getImportedPage(reader, p);
            canvas.addTemplate(page, 0, 0);
            document.newPage();
            while (p++ < n) {
                page = writer.getImportedPage(reader, p);
                canvas.addTemplate(page, 0, 0);
                page = writer.getImportedPage(reader, n);
                canvas.addTemplate(page, a4_width, 0);
                document.newPage();
                n--;
            }
        } else {
            p = 0;
            while (p++ < n) {
                page = writer.getImportedPage(reader, p);
                canvas.addTemplate(page, 0, 0);
                page = writer.getImportedPage(reader, n);
                canvas.addTemplate(page, a4_width, 0);
                document.newPage();
                n--;
            }
        }
        document.close();
        reader.close();

        try {
            String outFile = src.substring(0, src.indexOf(".pdf")) + ".pdf";
            PdfReader reader2 = new PdfReader(dest);
            int k = reader2.getNumberOfPages();
            reader2.selectPages(String.format("0", k));
            manipulateWithCopy(reader2, outFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Копирование документа для корректоного использования
     * @param src - путь к входному файлу
     */
    public static void manipulateWithCopy(PdfReader reader, String dest) throws IOException, DocumentException {
        int n = reader.getNumberOfPages();
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest));
        document.open();
        for (int i = 1; i <= n; ) {
            copy.addPage(copy.getImportedPage(reader, i++));
        }
        document.close();
    }
    /**
     * Печать документа
     *@param fileName - документ, который следует распечатать
     */
    public static void printPDF(PDDocument fileName) throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(fileName));
        job.printDialog();
        job.print();
    }
    /**
     * Чтение страниц из документа для изменения их последовательности
     */
    public static void GetReadyToPrint() throws IOException, DocumentException {
        String src = path.substring(0, path.indexOf(".pdf")) + "Temp" + ".pdf";
        PdfReader reader = new PdfReader(src);
        int k = reader.getNumberOfPages();
        reader.selectPages(String.format("0", k));
        manipulateWithStamper(reader, path);
        reader.close();
    }
    /**
     *Изменение страниц документа
     *@param reader - чтение документа
     *@param dest - расположение измененного файла
     */
    public static void manipulateWithStamper(PdfReader reader, String dest) throws IOException, DocumentException {
        int n = reader.getNumberOfPages();
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dest));
        document.open();

        for (int i = 1; i <= n; ) {
            copy.addPage(copy.getImportedPage(reader, i));
            i += 2;
        }

        for (int i = 2; i <= n; ) {
            copy.addPage(copy.getImportedPage(reader, i));
            i += 2;
        }
        document.close();
    }
    /**
     * Очистка листа от добавленных пользователем документов
     */
    public static void ClearDocument() {
        list.clear();
    }
}