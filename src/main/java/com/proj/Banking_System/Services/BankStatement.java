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
        LocalDate start=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        List<Transaction> transactionList=transactionRepository.findAll().stream().filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt().isEqual(start)).filter(transaction -> transaction.getCreatedAt().isEqual(end)).toList();

        User user=userRepository.findUserByAccountNumber(accountNumber);
        String customerName=user.getFirstName()+" "+user.getLastName();
        com.itextpdf.text.Rectangle statementsSize = new Rectangle(PageSize.A4.getLeft(), PageSize.A4.getBottom(), PageSize.A4.getRight(), PageSize.A4.getTop());
        Document document = new Document(statementsSize);
        log.info("setting size of document");
        OutputStream outputStream=new FileOutputStream(FILE);
        PdfWriter.getInstance(document,outputStream);
        document.open();

        PdfPTable bankInfoTable=new PdfPTable(1);
        PdfPCell bankName=new PdfPCell(new Phrase("Vikas's Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.GREEN);
        bankName.setPadding(10);

        PdfPCell bankAddress=new PdfPCell(new Phrase("Dhule Maharashtra 0001"));
        bankAddress.setBorder(0);
        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo=new PdfPTable(2);
        PdfPCell customerInfo= new PdfPCell(new Phrase("Date :"+startDate));
        customerInfo.setBorder(0);
        PdfPCell statement=new PdfPCell(new Phrase("Statement of Account "));
        statement.setBorder(0);
//        PdfPCell stopDate=new PdfPCell(new Phrase("End Date"+endDate));
//        stopDate.setBorder(0);
        PdfPCell name=new PdfPCell(new Phrase("Customer Name : "+customerName));
        name.setBorder(0);
        PdfPCell space=new PdfPCell();
        PdfPCell address=new PdfPCell(new Phrase("Customer Address : "+user.getAddress()));
        address.setBorder(0);

        PdfPTable transactionTable=new PdfPTable(4);
        PdfPCell date=new PdfPCell(new Phrase("Date"));
        date.setBackgroundColor(BaseColor.GREEN);
        date.setBorder(0);
        PdfPCell transactionType=new PdfPCell(new Phrase("Transaction Type"));
        transactionType.setBackgroundColor(BaseColor.GREEN);
        transactionType.setBorder(0);
        PdfPCell transactionAmount=new PdfPCell(new Phrase("Transaction Amount"));
        transactionAmount.setBackgroundColor(BaseColor.GREEN);
        transactionAmount.setBorder(0);
        PdfPCell status=new PdfPCell(new Phrase("Status"));
        status.setBackgroundColor(BaseColor.GREEN);
        status.setBorder(10);
        transactionTable.addCell(date);
        transactionTable.addCell(transactionType);
        transactionTable.addCell(transactionAmount);
        transactionTable.addCell(status);


        transactionList.forEach(transaction->{
            transactionTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionTable.addCell(new Phrase(transaction.getStatus()));

        });
        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);
        document.add(bankInfoTable);
        document.add(statementInfo );
        document.add(transactionTable);
        document.close();
        EmailDetails emailDetails=EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Statement of Account")
                .messageBody("Kindly find your requested account statements")
                .attachment(FILE)
                .build();
        emailService.sendEmailAttachment(emailDetails);

        return transactionList;
    }

}
