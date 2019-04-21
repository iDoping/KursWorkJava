package sample;
import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Brochure {

    public static void Split(String inFile) {
        List<InputStream> list = new ArrayList<InputStream>();
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
                list.add(new FileInputStream(new File(outFile)));
                document.close();
                writer.close();
            }
            OutputStream out = new FileOutputStream(new File(inFile.substring(0, inFile.indexOf(".pdf")) + "Форматированный" + ".pdf"));
            doMerge(list, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                PdfImportedPage page = writer.getImportedPage(reader, i);
                document.setPageSize(new Rectangle(page.getHeight(), page.getWidth()));
                document.newPage();
                float min = Math.min(page.getHeight(), page.getWidth());
                AffineTransform rotateInstance = AffineTransform.getRotateInstance(Math.PI/-2,min /1.9, min / 1.9);
                double[] matrix = new double[6];
                rotateInstance.getMatrix(matrix);
                cb.addTemplate(page, (float) matrix[0], (float) matrix[1], (float) matrix[2],(float) matrix[3], (float) matrix[4], (float) matrix[5]);
            }
        }
        outputStream.flush();
        document.close();
        outputStream.close();

    }

    public static void AddImage(String inFile) {
        try {
            Document document = new Document();
            PdfReader reader = new PdfReader(inFile);
            OutputStream output = new FileOutputStream(new File(inFile.substring(0, inFile.indexOf(".pdf")) + "cИзображением" + ".pdf"));
            PdfWriter writer = PdfWriter.getInstance(document, output);
            document.open();
            document.add(Image.getInstance("D:/Картинки/40.jpg"));
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}