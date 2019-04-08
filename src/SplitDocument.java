import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;


public class SplitDocument {

    private static final String RESULT = "C:/Users/Reado/Desktop/Lukin.pdf";

        public static void main(String[] args) {
            try {
                String inFile = "D:/Lukin.pdf".toLowerCase();
                System.out.println("Чтение " + inFile);
                PdfReader reader = new PdfReader(inFile);
                int n = reader.getNumberOfPages();

                int rot;
                PdfDictionary pageDict;
                for (int i = 1; i <= n; i++) {
                    rot = reader.getPageRotation(i);
                    pageDict = reader.getPageN(i);
                    pageDict.put(PdfName.ROTATE, new PdfNumber(rot + 90));
                }
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(RESULT));
                stamper.close();
                reader.close();

                System.out.println("Количество страниц : " + n);
                int i = 0;
                while (i < n) {
                    String outFile = inFile.substring(0, inFile.indexOf(".pdf")) + "-" + String.format("%03d", i + 1) + ".pdf";
                    System.out.println("Запись " + outFile);
                    Document document = new Document(reader.getPageSizeWithRotation(2)); //reader.getPageSizeWithRotation(1)
                    PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                    document.open();
                    PdfImportedPage page = writer.getImportedPage(reader, ++i);
                    writer.addPage(page);
                    document.close();
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}