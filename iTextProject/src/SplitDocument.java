import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class SplitDocument {

   // private static final String RESULT = "C:/Users/Reado/Desktop/Lukin.pdf";

        public static void main(String[] args) {
            List<InputStream> list = new ArrayList<InputStream>();
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
                //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(RESULT));
                //stamper.close();
               // reader.close();

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

                OutputStream out = new FileOutputStream(new File("D:/result.pdf"));
                doMerge(list, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }
}