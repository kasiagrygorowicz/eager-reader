package com.example.eagerreader.app.service.export;

import com.example.eagerreader.app.dto.author.AuthorDTO;
import com.example.eagerreader.app.dto.book.BookDTO;
import com.example.eagerreader.app.dto.book.BookDetailsDTO;
import com.example.eagerreader.app.entity.Author;
import com.example.eagerreader.app.entity.Book;
import com.example.eagerreader.app.entity.User;
import com.example.eagerreader.app.repository.AuthorRepository;
import com.example.eagerreader.app.repository.BookRepository;
import com.example.eagerreader.app.service.book.BookServiceImpl;
import com.example.eagerreader.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExportServiceImpl implements ExportService {

    private final UserService userService;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public void exportAuthorBooks(Long authorId) {
        Author author = authorRepository.getById(authorId);
        Workbook workbook = new XSSFWorkbook();
        String n ="books-of-"+author.getFirstname()+"-"+author.getLastname();
        Sheet sheet = workbook.createSheet(n);
        
        prepareBooksHeaders(sheet,workbook);
        List<BookDetailsDTO> books = author.getBooks().stream().map(BookServiceImpl.BookMapper::map3).collect(Collectors.toList());
        writeBooksToFile(sheet,workbook,books);
        try {
            saveFile(workbook,n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBooksToFile(Sheet sheet, Workbook workbook,List<BookDetailsDTO> books) {
        int rowIndex = 1;
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        for (BookDetailsDTO b : books) {
            Row row = sheet.createRow(rowIndex);
            createCell(row, b.getId().toString(), style, 0);
            createCell(row, b.getTitle(), style, 1);
            String a="";
            for(AuthorDTO au :b.getAuthors())
                a+=au.getFirstname()+" "+au.getLastname()+", ";
            createCell(row, a, style, 2);
            createCell(row, b.getDescription(), style, 3);
            createCell(row, String.valueOf(b.getPublished()), style, 4);
            createCell(row, b.getPublisher().getName(), style, 5);
            rowIndex++;
        }
    }

    @Override
    public void exportFavoriteBooks() {
        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Workbook workbook = new XSSFWorkbook();
        String n ="favoriteBooks-"+user.getEmail();
        Sheet sheet = workbook.createSheet(n);

        prepareBooksHeaders(sheet,workbook);
        List<Book> books =new ArrayList<>();
        List<Book> f =user.getFavorites();
        for(Book b:f){
            books.add((bookRepository.getById(b.getId())));
        }
        List<BookDetailsDTO> booksMapped = books.stream().map(BookServiceImpl.BookMapper::map3).collect(Collectors.toList());
        writeBooksToFile(sheet,workbook,booksMapped);
        try {
            saveFile(workbook,n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStreamResource readReport(File file) throws IOException {
        return new InputStreamResource(new FileInputStream(file));
    }


    private void prepareBooksHeaders(Sheet sheet, Workbook workbook) {
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 20000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(5, 7000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = getHeaderStyle(workbook);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Title");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Author(s)");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Synopsis");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Published");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Publisher");
        headerCell.setCellStyle(headerStyle);
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();;

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setBold(true);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private void saveFile(Workbook workbook, String fileName) throws IOException {
        String finalFileName = fileName + ".xlsx";
        FileOutputStream outputStream = new FileOutputStream(getClass().getClassLoader().getResource(".").getFile() +  finalFileName);
        workbook.write(outputStream);
        workbook.close();
    }


    private void createCell(Row row, String cellContent, CellStyle style, int cellIndex) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellContent);
        cell.setCellStyle(style);
    }

}
