import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.font.FontProvider;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class PdfUtils {

    // ./fonts/malgun.ttf
    private static final String fontPath = "." + File.separator + "fonts" + File.separator + "malgun.ttf";

    private static boolean createUploadPath(String uploadPath) {
        boolean result = true;
        String path = String.format("%s/", uploadPath);

        File file = new File(path);

        if (!file.isDirectory()) {
            file.mkdirs();

            try {
                Runtime.getRuntime().exec("chmod 755" + path);
            } catch (IOException e) {
                System.out.println("########### IOException ##############");
                System.out.println(e.getMessage());
                System.out.println("########### IOException ##############");
                return false;
            }
        }

        return result;
    }

    private static boolean createPdfFile(String htmlString, String uploadPath) {
        boolean result = false;
        PdfWriter writer = null;
        String randomPdfFileName = String.format("%s.%s",UUID.randomUUID().toString().replaceAll("-", ""), "pdf");
        String pdfPath = String.format("%s/%s", uploadPath, randomPdfFileName);

        try {
            ConverterProperties properties = new ConverterProperties();
            FontProvider fontProvider = new DefaultFontProvider(false, false, false);
            FontProgram font;

            font = FontProgramFactory.createFont(fontPath);
            fontProvider.addFont(font);
            properties.setFontProvider(fontProvider);

            List<IElement> elementList = HtmlConverter.convertToElements(htmlString, properties);

            writer = new PdfWriter(pdfPath);

            PdfDocument document = new PdfDocument(writer);
            PdfDocumentInfo pdfInfo = document.getDocumentInfo();
            pdfInfo.setTitle(randomPdfFileName);

            Document doc = new Document(document);

            doc.setMargins(20, 20, 20, 20);

            for (IElement el : elementList) {
                doc.add((IBlockElement) el);
            }

            doc.close();

            File createdPdfFile = new File(pdfPath);

            createdPdfFile.setExecutable(true, false);
            createdPdfFile.setReadable(true, false);
            createdPdfFile.setWritable(true, false);

            result = true;
        } catch (FileNotFoundException e) {
            System.out.println("########### FileNotFoundException ##############");
            System.out.println(e.getMessage());
            System.out.println("########### FileNotFoundException ##############");
            return false;
        } catch (IOException e) {
            System.out.println("########### IOException ##############");
            System.out.println(e.getMessage());
            System.out.println("########### IOException ##############");
            return false;
        } catch (Exception e) {
            System.out.println("########### Exception ##############");
            System.out.println(e.getMessage());
            System.out.println("########### Exception ##############");
            return false;
        }
        return result;
    }

    public static boolean makePdf(String htmlString, String uploadPath) {
        boolean result = false;

        if (createUploadPath(uploadPath)) {
            if(createPdfFile(htmlString, uploadPath)) {
                result = true;
            }
        }
        return result;
    }

    public static boolean makePdfFromFile(String htmlPath, String uploadPath) {

        boolean isReading = false;

        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;

        File file = new File(htmlPath);

        String temp = "";
        String htmlString = "";

        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            while((temp = bufferedReader.readLine()) != null) {
                htmlString += temp +"\n";
            }
            isReading = true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("########### FileNotFoundException ##############");
            System.out.println(e.getMessage());
            System.out.println("########### FileNotFoundException ##############");
        } catch (IOException e) {
            System.out.println("########### IOException ##############");
            System.out.println(e.getMessage());
            System.out.println("########### IOException ##############");
        } finally {
            try {
                fileInputStream.close();
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("########### Close IOException ##############");
                System.out.println(e.getMessage());
                System.out.println("########### Close IOException ##############");
            }
        }

        boolean result = false;
        if (isReading) {
            result = makePdf(htmlString, uploadPath);
        }

        return result;
    }
}


