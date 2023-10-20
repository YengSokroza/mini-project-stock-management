package com.company;


import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockManagement {
    public static void main(String[] args) {
        System.out.println("""

                 ██████╗███████╗████████╗ █████╗ ██████╗          ██╗ █████╗ ██╗   ██╗ █████╗    \s
                ██╔════╝██╔════╝╚══██╔══╝██╔══██╗██╔══██╗         ██║██╔══██╗██║   ██║██╔══██╗   \s
                ██║     ███████╗   ██║   ███████║██║  ██║         ██║███████║██║   ██║███████║   \s
                ██║     ╚════██║   ██║   ██╔══██║██║  ██║    ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║   \s
                ╚██████╗███████║   ██║   ██║  ██║██████╔╝    ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║   \s
                 ╚═════╝╚══════╝   ╚═╝   ╚═╝  ╚═╝╚═════╝      ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝   \s
                                                                                                 \s
                """);
        System.out.println("STOCK MANAGEMENT SYSTEM");
        Table table = getTable();


//        variebles  ---->
        Scanner s = new Scanner(System.in);
        String ch;
        LocalDate date = LocalDate.now();
        int currPage = 1;
        int  rowsPerPage = 2;

        //        List  ---->
        List<Product> products = new ArrayList<>();

        products.add(new Product(1,"Sting",2000,12, date));
        products.add(new Product(2,"SoyMilk",1500,10,date));
        products.add(new Product(3,"Vital",1000,13,date));
        products.add(new Product(4,"Coca Cola",2000,10, date));
        products.add(new Product(5,"Red Bull",2500,7,date));
        products.add(new Product(6,"Angkor Puro",1000,10,date));


//        Swtich case ---->
        do {
            System.out.println(table.render());
            System.out.print("Command ⟶ ");
            ch = s.nextLine().toLowerCase().trim();

            switch (ch){
                case "*" -> Crud.DisplayAll(products,currPage,rowsPerPage);
                case "w" -> Crud.WritePro(products);
                case "r" -> Crud.ReadPro(products);
                case "u" -> Crud.UpdatePro(products);
                case "d" -> Crud.DeletePro(products);
                case "f" -> currPage = Crud.First(currPage,rowsPerPage,products);
                case "p" -> currPage = Crud.Previous(currPage,rowsPerPage,products);
                case "n" -> currPage = Crud.Next(currPage,rowsPerPage,products);
                case "l" -> currPage = Crud.Last(currPage,rowsPerPage,products);
                case "s" -> Crud.SearchPro(products,currPage,rowsPerPage);
                case "g" -> currPage = Crud.Goto(currPage,rowsPerPage,products);
                case "se" -> rowsPerPage = Crud.SetRows(rowsPerPage,products);
                case "h" -> Crud.Help();
                case "e" ->{
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                default -> {
                    Scanner scanner = new Scanner(System.in);
                    String[] shortCut = ch.split("#");
                    switch ( shortCut[0] ) {
                        case "w", "W" -> {
                            try {
                                Product lastPro = products.get(products.size() - 1);
                                int proID = lastPro.getProId() + 1;

                                String[] afterShortCut = shortCut[1].split("-");

                                if (afterShortCut.length != 3) {
                                    System.out.println("Invalid format. Use 'w#Name-Price-Quantity' to add a product.");
                                    break;
                                }

                                String newName = afterShortCut[0];
                                double newPrice = Double.parseDouble(afterShortCut[1]);
                                int newQty = Integer.parseInt(afterShortCut[2]);

                                Product proD = new Product(proID, newName,newPrice ,newQty , LocalDate.now());

                                Table tb = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
                                tb.addCell(" ID            : " + proID + " ".repeat(10));
                                tb.addCell(" Name          : " + proD.getProName() + " ".repeat(10));
                                tb.addCell(" Unit price    : " + proD.getProPrice() + " ".repeat(10));
                                tb.addCell(" Qty           : " + proD.getProQty() + " ".repeat(10));
                                tb.addCell(" Imported Date : " + LocalDate.now() + " ".repeat(10));
                                System.out.println(tb.render());

                                System.out.print("Are you sure to add this record? [Y/y] or [N/n] : ");
                                String yn = scanner.nextLine();
                                switch (yn.toUpperCase()) {
                                    case "Y" -> {
                                        products.add(proD);
                                        Table tbl = new Table(1, BorderStyle.DESIGN_CAFE);
                                        tbl.addCell("  Product with ID [" + proID + "] added successfully  ");
                                        System.out.println(tbl.render());
                                    }
                                    case "N" -> {
                                        Table tbl = new Table(1, BorderStyle.DESIGN_CAFE);
                                        tbl.addCell("  Product with ID [" + proID + "] is not added  ");
                                        System.out.println(tbl.render());
                                    }
                                    default -> System.out.println("Invalid response. The product was not added.");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        case "r","R" -> {

                            try {
                                int proID = Integer.parseInt(shortCut[1]);
                                boolean isFound = false;
                                for (Product product : products) {
                                    if ( product.getProId().equals(proID) ) {
                                        Table tb = new Table(1,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.SURROUND);
                                        tb.addCell(" ID            : "+proID+" ".repeat(10));
                                        tb.addCell(" Name          : "+product.getProName()+" ".repeat(10));
                                        tb.addCell(" Unit price    : "+product.getProPrice()+" ".repeat(10));
                                        tb.addCell(" Qty           : "+product.getProQty()+" ".repeat(10));
                                        tb.addCell(" Imported Date : "+LocalDate.now()+" ".repeat(10));
                                        System.out.println(tb.render());
                                        isFound = true;
                                        break;
                                    }
                                }
                                if (!isFound){
                                    Table tb = new Table(1,BorderStyle.DESIGN_CAFE);
                                    tb.addCell("  Product with ID : "+shortCut[1]+" is not found  ");
                                    System.out.println(tb.render());
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        case "d","D" -> {

                            try {
                                int proID = Integer.parseInt(shortCut[1]);
                                boolean isFound = false;
                                for (Product product : products) {
                                    if (product.getProId().equals(proID)) {
                                        Table tbl = new Table(1,BorderStyle.UNICODE_BOX_DOUBLE_BORDER,ShownBorders.SURROUND);
                                        tbl.addCell(" ID            : "+proID+" ".repeat(10));
                                        tbl.addCell(" Name          : "+product.getProName()+" ".repeat(10));
                                        tbl.addCell(" Unit price    : "+product.getProPrice()+" ".repeat(10));
                                        tbl.addCell(" Qty           : "+product.getProQty()+" ".repeat(10));
                                        tbl.addCell(" Imported Date : "+LocalDate.now()+" ".repeat(10));
                                        System.out.println(tbl.render());
                                        System.out.print( "Are you sure to add this record? [Y/y] or [N/n] : ");
                                        String opt = scanner.nextLine();
                                        switch (opt) {
                                            case "y","Y" -> {
                                                products.remove(product);
                                                Table tb = new Table(1,BorderStyle.DESIGN_CAFE);
                                                tb.addCell("  Product with ID " + proID + " is deleted  ");
                                                System.out.println(tb.render());
                                            }
                                            case "n","N" -> {
                                                Table tb = new Table(1,BorderStyle.DESIGN_CAFE);
                                                tb.addCell("  Product with ID " + proID + " is removed  ");
                                                System.out.println(tb.render());
                                            }
                                            default -> System.out.println("Invalid options");
                                        }
                                        isFound = true;
                                        break;
                                    }
                                }
                                if (!isFound) {
                                    System.out.println("Invalid options");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        default -> System.err.println("Invalid Options");
                    }
                }


            }

        }while(true);

    }

//    table ------>
    private static Table getTable() {
        Table table = new Table(8, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.SURROUND);
        table.setColumnWidth(0,20,30);
        table.setColumnWidth(1,20,30);
        table.setColumnWidth(2,20,30);
        table.setColumnWidth(3,20,30);
        table.setColumnWidth(4,20,30);
        table.setColumnWidth(5,20,30);
        table.setColumnWidth(6,20,30);
        table.setColumnWidth(7,20,30);

        table.addCell(" *)Display");
        table.addCell("| W)rite");
        table.addCell("| R)ead");
        table.addCell("| U)pdate");
        table.addCell("| D)lete");
        table.addCell("| S)earch");
        table.addCell("| F)irst");
        table.addCell("| P)revious");
        table.addCell(" G)oto");
        table.addCell("| Se)t row");
        table.addCell("| H)elp");
        table.addCell("| E)xit");
        return table;
    }
}
