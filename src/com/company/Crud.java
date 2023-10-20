package com.company;


import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Crud {
//    variables ---------->
    static String proName;
    static Integer proQty;
    static double proPrice;
    static LocalDate importedDate = LocalDate.now();
    static String opt;
    static Scanner s = new Scanner(System.in);
    static Scanner sc = new Scanner(System.in);

    public static void ReadPro(List<Product> products){
//        for (Product i : products){
//            System.out.println(i);

//        }
        if(!products.isEmpty()){
            try{
                System.out.print("Enter Product Id to display: ");
                int id = s.nextInt();
                for(Product i : products){
                    if(id == i.getProId()){
                        Table readTable = getReadTable(i);

                        System.out.println(readTable.render());
                    }
                }

            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }else System.out.println("List is Empty...");

    }

    private static Table getReadTable(Product i) {
        Table readTable = new Table(2,BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        readTable.setColumnWidth(0,20,30);
        readTable.setColumnWidth(1,20,30);
        readTable.addCell("ID");
        readTable.addCell(": " + i.getProId());
        readTable.addCell("Name");
        readTable.addCell(": " + i.getProName());
        readTable.addCell("Unit Price");
        readTable.addCell(": " + i.getProPrice());
        readTable.addCell("QTY");
        readTable.addCell(": " + i.getProQty());
        readTable.addCell("Imported Date");
        readTable.addCell(": " + i.getImportedDate());
        return readTable;
    }

    public static void ReadById(List<Product> products , Integer proId){
        if(!products.isEmpty()){
            try{
                for(Product i : products){
                    if(proId.equals(i.getProId())){
                        Table readTable = getReadTable(i);
                        System.out.println(readTable.render());
                    }
                }

            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }else System.out.println("List is Empty...");




    }
    public static void WritePro(List<Product> products){
        int nextProId;
        if(!products.isEmpty()){
            Product lastPro = products.get(products.size() - 1);
            nextProId = lastPro.getProId() + 1;
        }else {
            nextProId = 1;
        }

        System.out.print("Enter Product Name: ");
        proName = sc.nextLine();
        System.out.print("Enter Unit Price: ");
        proPrice = s.nextDouble();
        System.out.print("Enter Product Qty: ");
        proQty = s.nextInt();

        System.out.println("Are you sure to add this record ? Yes : [Y/y] , No : [N/n] ");
        opt = sc.nextLine();

        if(opt.equals("Y") || opt.equals("y")){
            products.add(new Product(nextProId,proName,proPrice,proQty,importedDate));
            System.out.println(nextProId + " was add successfully!");
        } else if (opt.equals("N") || opt.equals("n")) {
            System.out.println("Cancel...");
        }else{
            System.out.println("Invalid...");
        }

    }

    public static void DisplayAll(List<Product> products, int currPage, int rowsPerPage ){
        int startIndex = (currPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, products.size());

        List<Product> proPage = products.subList(startIndex, endIndex);

        Table displayTable = getDisplayTable(proPage);

        System.out.println(displayTable.render());
        String separator = "~ ".repeat(52);
        int totalPages = (int) Math.ceil((double) products.size() / rowsPerPage);
        String pageInfo = String.format("Page %d of %d %s Total records: %d", currPage, totalPages, " ".repeat(40), products.size());

        System.out.println(separator);
        System.out.println(pageInfo);
        System.out.println(separator);

    }

    private static Table getDisplayTable(List<Product> proPage) {
        Table displayTable = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        displayTable.addCell(" ID");
        displayTable.addCell("NAME");
        displayTable.addCell("UNIT PRICE");
        displayTable.addCell("QTY");
        displayTable.addCell("IMPORTED DATE");
        displayTable.setColumnWidth(0,20,30);
        displayTable.setColumnWidth(1,20,30);
        displayTable.setColumnWidth(2,20,30);
        displayTable.setColumnWidth(3,20,30);
        displayTable.setColumnWidth(4,20,30);

        for (Product i : proPage){
            displayTable.addCell(" " + i.getProId());
            displayTable.addCell(i.getProName());
            displayTable.addCell("" + i.getProPrice());
            displayTable.addCell("" + i.getProQty());
            displayTable.addCell("" + i.getImportedDate());
        }
        return displayTable;
    }

    public static int First(int currPage, int rowsPerPage, List<Product> products) {
        currPage = Math.max(currPage, 1); //Compare value of currpage and 1 and give the biggest value (Ensure currentPage is at least 1)
        if(currPage == 1){
            System.out.println("You are already on the first page.");
        }else{
            currPage = 1;
            DisplayAll(products, currPage, rowsPerPage);
        }
        return currPage;
    }

    public static int Previous(int currPage, int rowsPerPage, List<Product> products) {
        if (currPage > 1) {
            currPage--;
            DisplayAll(products, currPage, rowsPerPage);
        } else {
            System.out.println("You are already on the first page.");
        }
        return currPage;
    }

    public static int Next(int currPage, int rowsPerPage, List<Product> products) {
        int totalPages = (int) Math.ceil((double) products.size() / rowsPerPage);
        currPage = Math.min(currPage + 1, totalPages);
        if (currPage <= totalPages) {
            DisplayAll(products, currPage, rowsPerPage);
        } else {
            System.out.println("You are already on the last page.");
        }
        return currPage;
    }

    public static int Last(int currPage, int rowsPerPage, List<Product> products) {
        int totalPages = (int) Math.ceil((double) products.size() / rowsPerPage);
        if (currPage == totalPages) {
            System.out.println("You are already on the last page.");
        } else {
            currPage = totalPages;
            DisplayAll(products, currPage, rowsPerPage);
        }
        return currPage;
    }

    public static int Goto(int currPage, int rowsPerPage, List<Product> products){
        int totalPages = (int) Math.ceil((double) products.size() / rowsPerPage);
        try{
            System.out.print("Go to Page: ");
            int pageNum = s.nextInt();
            if(pageNum <= totalPages){
                currPage = pageNum;
                DisplayAll(products, currPage, rowsPerPage);
            }else{
                System.out.println("Invalid Page Number...");
            }

        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter a valid Page Number.");
        }
        return currPage;
    }

    public static int SetRows( int rowsPerPage, List<Product> products){
        try{
            System.out.print("Set rows to display : ");
            int row = s.nextInt();
            if((row >= 1) && (row < products.size())) rowsPerPage = row;

        }catch (InputMismatchException e){
            System.out.println("Invalid Page Number...");
        }
        return rowsPerPage;
    }

    public static void DeletePro(List<Product> products){
        List<Product> copyProList = new ArrayList<>(products);
        if(!products.isEmpty()){
            try{
                System.out.print("Enter Product Id to Delete : ");
                int id = s.nextInt();
                for(Product i : copyProList){
                    if(id == i.getProId()){
                        ReadById(products,id);

                        System.out.println("Are you sure to delete this record ? Yes : [Y/y] , No : [N/n] ");
                        opt = sc.nextLine();

                        if(opt.equals("Y") || opt.equals("y")){
                            products.remove(i);
                            System.out.println("Product was removed successfully!");
                        } else if (opt.equals("N") || opt.equals("n")) {
                            System.out.println("Cancel...");
                        }else{
                            System.out.println("Invalid...");
                        }
                    }
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }else System.out.println("List is Empty...");
    }

    public static void UpdatePro(List<Product> products){
        int ch;
        if(!products.isEmpty()){
            try{
                System.out.print("Enter Product Id to Update : ");
                int id = s.nextInt();
                for(Product i : products){
                    if(id == i.getProId()){
                        ReadById(products,id);

                            System.out.println("What do you want to update?");
                            Table updateTable = getUpdateTable();
                            System.out.println(updateTable.render());
                            System.out.print("Enter your options (1-5): ");
                            ch = s.nextInt();

                            switch (ch){
                                case 1 -> {
                                    System.out.print("Enter New Product name: ");
                                    proName = sc.nextLine();

                                    System.out.print("Enter New Product Unit Price:");
                                    proPrice = s.nextDouble();

                                    System.out.print("Enter New Product Quantity:");
                                    proQty = s.nextInt();

//                                    after update products table  ------>
                                    Table updateAll = getTableAfterUpdate(id,proName,proPrice,proQty);
                                    System.out.println(updateAll.render());

                                    System.out.println("Are you sure to Update this record ? Yes : [Y/y] , No : [N/n] ");
                                    opt = sc.nextLine();

                                    if(opt.equals("Y") || opt.equals("y")){
//                                        i.setProName(proName);
//                                        i.setProPrice(proPrice);
//                                        i.setProQty(proQty);
//                                        i.setImportedDate(LocalDate.now());
                                        int index = products.indexOf(i);
                                        products.set(index,new Product(id,proName,proPrice,proQty,LocalDate.now()));

                                        System.out.println("Product was updated successfully!");
                                    } else if (opt.equals("N") || opt.equals("n")) {
                                        System.out.println("Cancel...");
                                    }else{
                                        System.out.println("Invalid...");
                                    }
                                }
                                case 2 -> {
                                    System.out.println("Enter New Product name: ");
                                    proName = sc.nextLine();

                                    Table updateName = getTableAfterUpdate(id,proName,i.getProPrice(),i.getProQty());
                                    System.out.println(updateName.render());

                                    System.out.println("Are you sure to Update this record ? Yes : [Y/y] , No : [N/n] ");
                                    opt = sc.nextLine();

                                    if(opt.equals("Y") || opt.equals("y")){
                                        i.setProName(proName);

                                        System.out.println("Product was updated successfully!");
                                    } else if (opt.equals("N") || opt.equals("n")) {
                                        System.out.println("Cancel...");
                                    }else{
                                        System.out.println("Invalid...");
                                    }
                                }
                                case 3 -> {
                                    System.out.println("Enter New Product Unit Price:");
                                    proPrice = s.nextDouble();

                                    Table updateUnitPrice = getTableAfterUpdate(id,i.getProName(),proPrice,i.getProQty());
                                    System.out.println(updateUnitPrice.render());

                                    System.out.println("Are you sure to Update this record ? Yes : [Y/y] , No : [N/n] ");
                                    opt = sc.nextLine();

                                    if(opt.equals("Y") || opt.equals("y")){
                                        i.setProPrice(proPrice);
                                        System.out.println("Product was updated successfully!");
                                    } else if (opt.equals("N") || opt.equals("n")) {
                                        System.out.println("Cancel...");
                                    }else{
                                        System.out.println("Invalid...");
                                    }
                                }
                                case 4 -> {
                                    System.out.print("Enter New Product Quantity:");
                                    proQty = s.nextInt();

                                    Table updateQty = getTableAfterUpdate(id,i.getProName(),i.getProPrice(),proQty);
                                    System.out.println(updateQty.render());

                                    System.out.println("Are you sure to Update this record ? Yes : [Y/y] , No : [N/n] ");
                                    opt = sc.nextLine();

                                    if(opt.equals("Y") || opt.equals("y")){
                                        i.setProQty(proQty);
                                        System.out.println("Product was removed successfully!");
                                    } else if (opt.equals("N") || opt.equals("n")) {
                                        System.out.println("Cancel...");
                                    }else{
                                        System.out.println("Invalid...");
                                    }
                                }
                                case 5 -> System.out.println("Back to main menu...");
                                default -> System.err.println("Invalid Choice...");
                            }
                    }else System.out.println("Product ID is not found");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }else System.out.println("List is Empty...");
    }

    private static Table getTableAfterUpdate(Integer id,String proName,double proPrice,Integer proQty){
        Table afterUpdateTable = new Table(2,BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        afterUpdateTable.setColumnWidth(0,20,30);
        afterUpdateTable.setColumnWidth(1,20,30);
        afterUpdateTable.addCell("ID");
        afterUpdateTable.addCell(": " + id);
        afterUpdateTable.addCell("Name");
        afterUpdateTable.addCell(": " + proName);
        afterUpdateTable.addCell("Unit Price");
        afterUpdateTable.addCell(": " + proPrice);
        afterUpdateTable.addCell("QTY");
        afterUpdateTable.addCell(": " + proQty);
        afterUpdateTable.addCell("Imported Date");
        afterUpdateTable.addCell(": " + LocalDate.now());
        return afterUpdateTable;
    }

    private static Table getUpdateTable() {
        Table updateTable = new Table(5,BorderStyle.UNICODE_BOX,ShownBorders.SURROUND);
        updateTable.setColumnWidth(0,20,30);
        updateTable.setColumnWidth(1,20,30);
        updateTable.setColumnWidth(2,20,30);
        updateTable.setColumnWidth(3,20,30);
        updateTable.setColumnWidth(4,20,30);
        updateTable.addCell("1. All");
        updateTable.addCell("2. Name");
        updateTable.addCell("3. Unit Price");
        updateTable.addCell("4. Quantity");
        updateTable.addCell("5. Back to Menu");
        return updateTable;
    }

    public static void SearchPro(List<Product> productList, int currPage, int rowsPerPage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search product by keyword: ");
        String searchKeyword = scanner.nextLine().toLowerCase();

        List<Product> matchingProducts = new ArrayList<>();

        for (Product product : productList) {
            String productName = product.getProName().toLowerCase();

            if (productName.contains(searchKeyword)) {
                matchingProducts.add(product);
            }
        }

        int totalPages = (int) Math.ceil((double) matchingProducts.size() / rowsPerPage);
        if (matchingProducts.isEmpty()) {
            System.out.println("No products found containing the keyword '" + searchKeyword + "'.");
        } else {
            if (currPage < 1) {
                currPage = 1;
            } else if (currPage > totalPages) {
                currPage = totalPages;
            }

            Table tableDisplay = getTableSearchDisplay(currPage, rowsPerPage, matchingProducts);

            System.out.println(tableDisplay.render());
            System.out.println("~ ".repeat(60));

            System.out.println("Page " + currPage + " of " + totalPages + " ".repeat(40) + "Total matching products: " + matchingProducts.size());
            System.out.println("~ ".repeat(60));
        }
    }

    private static Table getTableSearchDisplay(int currPage, int rowsPerPage, List<Product> matchingProducts) {
        int startIndex = (currPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, matchingProducts.size());

        Table tableDisplay = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        tableDisplay.addCell(" ".repeat(2) + "ID" + " ".repeat(2));
        tableDisplay.addCell(" ".repeat(2) + "Name" + " ".repeat(2));
        tableDisplay.addCell(" ".repeat(2) + "Unit Price" + " ".repeat(2));
        tableDisplay.addCell(" ".repeat(2) + "Qty" + " ".repeat(2));
        tableDisplay.addCell(" ".repeat(2) + "Imported Date" + " ".repeat(2));

        for (int i = startIndex; i < endIndex; i++) {
            Product product = matchingProducts.get(i);
            tableDisplay.addCell(" ".repeat(2) + product.getProId().toString());
            tableDisplay.addCell(" ".repeat(2) + product.getProName());
            tableDisplay.addCell(" ".repeat(2) + product.getProPrice().toString());
            tableDisplay.addCell(" ".repeat(2) + product.getProQty().toString());
            tableDisplay.addCell(" ".repeat(2) + product.getImportedDate().toString());
        }
        return tableDisplay;
    }


    public static void Help(){
        Table helpListTable = new Table(1,BorderStyle.CLASSIC_COMPATIBLE_LIGHT_WIDE,ShownBorders.SURROUND);
        helpListTable.addCell("1. \tPress\t * : Display all record of products");
        helpListTable.addCell("2. \tPress\t w : Add new product");
        helpListTable.addCell("   \tPress\t w ->#proName-unitPrice-qty : shortcut to add new product");
        helpListTable.addCell("3. \tPress\t r : Read Content any content");
        helpListTable.addCell("   \tPress\t r ->#proID : shortcut to read product by ID");
        helpListTable.addCell("4. \tPress\t u : Update data");
        helpListTable.addCell("5. \tPress\t d : Delete data");
        helpListTable.addCell("   \tPress\t d ->#proID : shortcut to delete product by ID");
        helpListTable.addCell("6. \tPress\t f : Display first page");
        helpListTable.addCell("7. \tPress\t p : Display previous page");
        helpListTable.addCell("8. \tPress\t n : Display next page");
        helpListTable.addCell("9. \tPress\t l : Display last page");
        helpListTable.addCell("10.\tPress\t s : Search product by name");
        helpListTable.addCell("11.\tPress\t h : Help");
        System.out.println(helpListTable.render());
    }


}
