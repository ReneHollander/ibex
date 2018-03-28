package at.hollander.ibex.view.pdf.impl;

import at.hollander.ibex.entity.Invoice;
import at.hollander.ibex.entity.Order;
import at.hollander.ibex.entity.OrderItem;
import at.hollander.ibex.view.pdf.AbstractITextPdfView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoicePDF extends AbstractITextPdfView {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static ModelAndView create(Invoice invoice) {
        Map<String, Object> model = new HashMap<>();
        model.put("invoice", invoice);
        return new ModelAndView(new InvoicePDF(), model);
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Invoice invoice = getInvoice(model);
        List<Order> orders = invoice.getOrders();

        Paragraph header = new Paragraph("Rechnung " + invoice.getId());
        header.setSpacingAfter(10);
        doc.add(header);

        doc.add(new Paragraph(invoice.getDate().toString()));

        doc.newPage();

        for (Order order : orders) {
            createOrder(order, doc);
        }
    }

    private void createOrder(Order order, Document doc) throws DocumentException {
        doc.add(new Paragraph("Bestellung: " + order.getId()));
        doc.add(new Paragraph("Bestelldatum: " + order.getOrderTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))));
        doc.add(new Paragraph("Lieferdatum: " + order.getDeliveryTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss"))));
        doc.add(new Paragraph("Adresse: " + order.getAddress() + ", " + order.getPostcode() + " " + order.getCity()));
        doc.add(new Paragraph("Hinweis: " + order.getDeliveryNote()));

        PdfPTable itemTable = new PdfPTable(4);
        itemTable.setWidthPercentage(100);
        itemTable.setSpacingBefore(10);
        itemTable.setSpacingAfter(20);

        itemTable.setHeaderRows(3);
        itemTable.addCell("Produkt");
        itemTable.addCell("Menge");
        itemTable.addCell("Einzelpreis");
        itemTable.addCell("Gesamtpreis");

        itemTable.setFooterRows(2);

        PdfPCell deliveryFeeCellText = new PdfPCell(new Phrase("Liefergebühr"));
        deliveryFeeCellText.setColspan(3);
        itemTable.addCell(deliveryFeeCellText);
        itemTable.addCell(new Phrase(String.valueOf(order.getPriceShipping())));

        PdfPCell totalCellText = new PdfPCell(new Phrase("Gesamt"));
        totalCellText.setColspan(3);
        itemTable.addCell(totalCellText);
        itemTable.addCell(new Phrase(String.valueOf(order.getPriceTotal())));

        for (OrderItem item : order.getItems()) {
            itemTable.addCell(item.getProductName());
            itemTable.addCell(String.valueOf(item.getAmount()));
            itemTable.addCell(String.valueOf(item.getPricePerItem()));
            itemTable.addCell(item.getPricePerItem().multiply(BigDecimal.valueOf(item.getAmount())).toString());
        }

        doc.add(itemTable);
    }

    @Override
    protected String getName(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        return "Rechnung-" + DATE_FORMAT.format(getInvoice(model).getId());
    }

    @SuppressWarnings("unchecked")
    private Invoice getInvoice(Map<String, Object> model) {
        return (Invoice) model.get("invoice");
    }

}