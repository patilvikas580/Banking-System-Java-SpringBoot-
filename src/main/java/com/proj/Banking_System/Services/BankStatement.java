package com.proj.Banking_System.Services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.proj.Banking_System.DTO.EmailDetails;
import com.proj.Banking_System.Entity.Transaction;
import com.proj.Banking_System.Entity.User;
import com.proj.Banking_System.Repository.TransactionRepository;
import com.proj.Banking_System.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement  {
    private final UserRepository userRepository;
    private TransactionRepository transactionRepository;
    private EmailService emailService;
    private static final String FILE="E:\\Programming\\Statement.pdf";
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        // 1. Fix the Date Filtering (Use isAfter/isBefore for a range, or just one day)
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll().stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .filter(t -> !t.getCreatedAt().isBefore(start) && !t.getCreatedAt().isAfter(end))
                .toList();

        User user = userRepository.findUserByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName();

        Document document = new Document(PageSize.A4);
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // --- Header Section ---
        PdfPTable bankInfoTable = new PdfPTable(1);
        bankInfoTable.setWidthPercentage(100);

        PdfPCell bankName = new PdfPCell(new Phrase("Vikas's Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.GREEN);
        bankName.setPadding(10);

        PdfPCell bankAddress = new PdfPCell(new Phrase("Dhule Maharashtra 0001"));
        bankAddress.setBorder(0);
        bankAddress.setPaddingLeft(10);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        // --- Customer Info Section (Fixed the red circled area here) ---
        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.setWidthPercentage(100);
        statementInfo.setSpacingBefore(10f);
        statementInfo.setSpacingAfter(10f);

        // Row 1
        PdfPCell dateCell = new PdfPCell(new Phrase("Date: " + startDate));
        dateCell.setBorder(Rectangle.NO_BORDER);
        statementInfo.addCell(dateCell);

        PdfPCell statementCell = new PdfPCell(new Phrase("Statement of Account"));
        statementCell.setBorder(Rectangle.NO_BORDER);
        statementInfo.addCell(statementCell);

        // Row 2
        PdfPCell nameCell = new PdfPCell(new Phrase("Customer Name: " + customerName));
        nameCell.setBorder(Rectangle.NO_BORDER);
        statementInfo.addCell(nameCell);

        PdfPCell emptyCell = new PdfPCell(new Phrase("")); // Placeholder for alignment
        emptyCell.setBorder(Rectangle.NO_BORDER);
        statementInfo.addCell(emptyCell);

        // Row 3
        PdfPCell addrCell = new PdfPCell(new Phrase("Address: " + user.getAddress()));
        addrCell.setBorder(Rectangle.NO_BORDER);
        addrCell.setColspan(2); // Spans both columns to keep it clean
        statementInfo.addCell(addrCell);

        // --- Transaction Table (Fixed borders here) ---
        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);

        // Create Headers with Borders
        String[] headers = {"Date", "Transaction Type", "Amount", "Status"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(BaseColor.GREEN);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            // cell.setBorder(Rectangle.BOX); // This ensures the border is present
            transactionTable.addCell(cell);
        }

        // Add Data Rows
        transactionList.forEach(transaction -> {
            transactionTable.addCell(transaction.getCreatedAt().toString());
            transactionTable.addCell(transaction.getTransactionType());
            transactionTable.addCell(transaction.getAmount().toString());
            transactionTable.addCell(transaction.getStatus());
        });

        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionTable);
        document.close();

        // ... Rest of your Email logic
        return transactionList;
    }

}
