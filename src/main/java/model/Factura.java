package model;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.fop.apps.*;
import org.jdom2.transform.JDOMSource;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class Factura {
    private Document document;

    public Factura(String xmlFilePath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(xmlFilePath);
            document = builder.build(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void imprimirXML() {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editarElemento(String xpath, String nuevoValor) {
        XPathExpression<Element> expression = XPathFactory.instance().compile(xpath, Filters.element());
        Element element = expression.evaluateFirst(document);
        if (element != null) {
            element.setText(nuevoValor);
        }
    }

    public void guardarCambios(String xmlFilePath) {
        try {
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(xmlFilePath));
            imprimirXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void crearPDF(String xslFilePath, String pdfFilePath) {
        try {
            FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFilePath));
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xslFilePath)));
            Source src = new JDOMSource(document);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}