/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kore.cashregister.print;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.kore.cashregister.App;
import org.kore.cashregister.OrderEntry;
import org.kore.cashregister.OrderPrinter;

/**
 *
 * @author Konrad Renner
 */
public class ReceiptPrinter implements OrderPrinter {
    
    private final double fontSize;

    public ReceiptPrinter() {
        this.fontSize = Double.parseDouble(System.getProperty("printing.font.size", "10"));
    }
    
    

    @Override
    public void print(long orderId, String cashierName, Instant orderTime, List<OrderEntry> entries, BigDecimal total) {
        System.out.println("Timestamp of Order:" + orderTime.atZone(ZoneId.systemDefault()));
        System.out.println("Total:" + total);
        System.out.println("Entries:" + entries);

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {

            Printer printer = job.getPrinter();
            // Show the printer job status
//            jobStatus.textProperty().bind(job.jobStatusProperty().asString());
            // Print the node
            PageLayout defaultPageLayout = printer.getDefaultPageLayout();
            PageLayout customLayout = printer.createPageLayout(defaultPageLayout.getPaper(), PageOrientation.PORTRAIT, 0, 0, 0, 0);
            System.out.println("Printable width:"+customLayout.getPrintableWidth());
            boolean printed = job.printPage(customLayout,
                    createPrintNode(orderId, cashierName, orderTime, entries, total, customLayout.getPrintableWidth()));

            if (printed) {
                // End the printer job
                job.endJob();
            } else {
                // Write Error Message
//                jobStatus.textProperty().unbind();
//                jobStatus.setText("Printing failed.");
            }
        }
    }

    Separator createNewSeparator(double maxWidth) {
        Separator separator = new Separator();
        separator.setPrefWidth(maxWidth);
        separator.setHalignment(HPos.LEFT);
        return separator;
    }

    ImageView createLogoView(double maxWidth) {
        Image image = new Image(App.class.getResourceAsStream("/images/logo_stuben_grey.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(maxWidth);
        imageView.setFitHeight(maxWidth / 5);
        return imageView;
    }

    void addMetainformations(ObservableList<Node> printList, long orderId, String cashierName, Instant orderTime, double maxWidth) {
        TextFlow cashier = new TextFlow(createTextWithSettings("Kassier:  "), createTextWithSettings(cashierName));
        cashier.setPrefWidth(maxWidth);

        TextFlow orderNumber = new TextFlow(createTextWithSettings("Bon-Nr.: "), createTextWithSettings(Long.toString(orderId)));
        orderNumber.setPrefWidth(maxWidth);

        TextFlow orderTst = new TextFlow(createTextWithSettings("Datum:   "),
                createTextWithSettings(orderTime.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
        orderTst.setPrefWidth(maxWidth);

        printList.add(cashier);
        printList.add(orderNumber);
        printList.add(orderTst);
    }

    void addOrders(ObservableList<Node> printList, List<OrderEntry> entries, double maxWidth) {
        final double sectionSize = maxWidth / 5;
        final double sectionPart = sectionSize / 4;

        //following: some magic numbers :-) 
        final double widthCount = sectionPart * 3;
        final double widthName = sectionSize * 3 + sectionPart * 2;
//        final double widthSinglePrice = sectionSize + sectionPart * 2;
        final double widthTotalPrice = sectionSize + sectionPart * 3;

        addOrdersHead(widthCount, widthName, widthTotalPrice, maxWidth, printList);

        entries.stream().forEach(entry -> {
            Label count = createLabelWithSettings(Integer.toString(entry.getAmount()));
            count.setPrefWidth(widthCount);
            count.setAlignment(Pos.CENTER_RIGHT);

            Label name = createLabelWithSettings(entry.getDescription());
            name.setPrefWidth(widthName);
            name.setAlignment(Pos.CENTER_LEFT);

//            Label single = createLabelWithSettings(entry.getUnitPrice().toPlainString());
//            single.setPrefWidth(widthSinglePrice);
//            single.setAlignment(Pos.CENTER_RIGHT);

            Label total = createLabelWithSettings(entry.getUnitPrice().multiply(BigDecimal.valueOf(entry.getAmount())).toPlainString());
            total.setPrefWidth(widthTotalPrice);
            total.setAlignment(Pos.CENTER_RIGHT);

            HBox order = new HBox(count, name, total);
            order.setPadding(new Insets(0, 1, 0, 1));
            order.setPrefWidth(maxWidth);
            printList.add(order);
        });
    }

    private void addOrdersHead(final double widthCount, final double widthName, final double widthTotalPrice, double maxWidth, ObservableList<Node> printList) {
        Label countHead = createLabelWithSettings(" "); //Nothing at the moment
        countHead.setStyle("-fx-font-weight: bold");
        countHead.setPrefWidth(widthCount);
        countHead.setAlignment(Pos.CENTER_LEFT);

        Label nameHead = createLabelWithSettings("Artikel");
        nameHead.setStyle("-fx-font-weight: bold");
        nameHead.setPrefWidth(widthName);
        nameHead.setAlignment(Pos.CENTER_LEFT);

//        Label singleHead = createLabelWithSettings("Einzel");
//        singleHead.setStyle("-fx-font-weight: bold");
//        singleHead.setPrefWidth(widthSinglePrice);
//        singleHead.setAlignment(Pos.CENTER_RIGHT);

        Label totalHead = createLabelWithSettings("Gesamt");
        totalHead.setStyle("-fx-font-weight: bold");
        totalHead.setPrefWidth(widthTotalPrice);
        totalHead.setAlignment(Pos.CENTER_RIGHT);

        HBox ordersHead = new HBox(countHead, nameHead, totalHead);
        ordersHead.setPadding(new Insets(0, 1, 0, 1));
        ordersHead.setPrefWidth(maxWidth);
        printList.add(ordersHead);
    }

    void addAmount(ObservableList<Node> printList, BigDecimal total, double maxWidth) {
        double width = maxWidth / 2;

        Label sumLabel = createLabelWithSettings("Summe:");
        sumLabel.setPrefWidth(width);
        sumLabel.setAlignment(Pos.CENTER_LEFT);

        Label sum = createLabelWithSettings(total.toPlainString());
        sum.setStyle("-fx-font-weight: bold");
        sum.setPrefWidth(width);
        sum.setAlignment(Pos.CENTER_RIGHT);

        HBox sumBox = new HBox(sumLabel, sum);
        sumBox.setPrefWidth(maxWidth);
        Label payment = createLabelWithSettings("Bar bezahlt");
        payment.setStyle("-fx-font-weight: bold");
        printList.add(sumBox);
        printList.add(payment);
    }

    void addFinishing(ObservableList<Node> printList, double maxWidth) {
        Label finishing = new Label("", createTextWithSettings("Die FF Stuben dankt!"));
        finishing.setStyle("-fx-font-weight: bold");
        finishing.setAlignment(Pos.CENTER);
        finishing.setPrefWidth(maxWidth);
        printList.add(finishing);
    }

    Node createPrintNode(long orderId, String cashierName, Instant orderTime, List<OrderEntry> entries, BigDecimal total, double maxWidth) {
        VBox baseBox = new VBox();
        baseBox.setPadding(new Insets(0, 1, 0, 1));
        baseBox.setMaxWidth(maxWidth);
        ObservableList<Node> children = baseBox.getChildren();
        children.add(createLogoView(maxWidth));
        children.add(createNewSeparator(maxWidth));
        addMetainformations(children, orderId, cashierName, orderTime, maxWidth);
        children.add(createNewSeparator(maxWidth));
        addOrders(children, entries, maxWidth);
        children.add(createNewSeparator(maxWidth));
        addAmount(children, total, maxWidth);
        children.add(createNewSeparator(maxWidth));
        addFinishing(children, maxWidth);
        return baseBox;
    }
    
    Label createLabelWithSettings(String text){
        Label l = new Label(text);
        l.setFont(Font.font(this.fontSize));
        return l;
    }
    
    Text createTextWithSettings(String text){
        Text l = new Text(text);
        l.setFont(Font.font(this.fontSize));
        return l;
    }
}
