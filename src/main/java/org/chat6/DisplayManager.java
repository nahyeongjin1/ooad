package org.chat6;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayManager extends JFrame {
    JPanel currentPanel;
    AuthenticationCode authenticationCode;
    CardCompany cardCompany;
    StockManager stockManager;
    AdminManager adminManager;
    PrepaymentManager prepaymentManager;
    VMController vmController;

    DisplayManager(AuthenticationCode authenticationCode, CardCompany cardCompany, StockManager stockManager, AdminManager adminManager, VMController vmController, PrepaymentManager prepaymentManager) {
        this.vmController = vmController;
        this.authenticationCode = authenticationCode;
        this.cardCompany = cardCompany;
        this.stockManager = stockManager;
        this.adminManager = adminManager;
        this.prepaymentManager = prepaymentManager;
        setVisible(true);
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        printMainScene();
    }

    void printMainScene() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton codeBtn = new JButton("Authentication Code");
        JButton cardBtn = new JButton("Card");
        JButton adminBtn = new JButton("Admin");

        currentPanel.add(codeBtn);
        currentPanel.add(adminBtn);
        currentPanel.add(cardBtn);
        codeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            clickInputCode();
        });
        cardBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            clickInputCard();
        });
        adminBtn.addActionListener(e -> {
            System.out.println("admin");
            currentPanel.setVisible(false);
            clickInputAdmin();
        });
        add(currentPanel);
        revalidate();


    }

    void clickInputCard() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel("Card");
        JLabel errorlabel = new JLabel("fail.");
        JTextField input = new JTextField(20);
        JButton submitBtn = new JButton("Submit");

        errorlabel.setVisible(false);

        JFrame frame = new JFrame("5x4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        String[][] data = {
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"},
                {"17", "18", "19", "20"}
        };

        String[] columnNames = {"1", "2", "3", "4"};
        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);


        currentPanel.add(homeBtn, BorderLayout.PAGE_START);
        currentPanel.add(label, BorderLayout.WEST);
        currentPanel.add(input, BorderLayout.CENTER);
        currentPanel.add(errorlabel);
        currentPanel.add(submitBtn, BorderLayout.PAGE_END);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            int result = cardCompany.requestValidCard(input.getText()); // 유효하면 잔액 반환, 유효하지 않으면-1반환
            System.out.println(result);
            if (result==-1) {
                showErrorMessage(errorlabel);
            } else {
                Map<String, Integer> cardInfo = new HashMap<>();
                cardInfo.put(input.getText(),result);
                vmController.setCardInfo(cardInfo);
                currentPanel.setVisible(false);
                showItems();
            }
        });
        add(currentPanel);


    }

    void clickInputCode() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel("Authentication Code");
        JLabel errorlabel = new JLabel("fail.");
        JTextField input = new JTextField(20);
        JButton submitBtn = new JButton("Submit");

        errorlabel.setVisible(false);

        currentPanel.add(homeBtn, BorderLayout.PAGE_START);
        currentPanel.add(label, BorderLayout.WEST);
        currentPanel.add(errorlabel);
        currentPanel.add(input, BorderLayout.CENTER);
        currentPanel.add(submitBtn, BorderLayout.PAGE_END);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            boolean result = authenticationCode.validateCode(input.getText());
            System.out.println(result);
            if (result) {
                currentPanel.setVisible(false);
                printMsgAndMainScene("good deal");
            } else {
                showErrorMessage(errorlabel);
            }
        });

        add(currentPanel);
    }

    void clickInputAdmin() {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JTextField inputID = new JTextField(10);
        JTextField inputPW = new JTextField(10);
        JButton submitBtn = new JButton("Submit");

        currentPanel.add(homeBtn);
        currentPanel.add(inputID);
        currentPanel.add(inputPW);
        currentPanel.add(submitBtn);
        add(currentPanel);
        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            adminManager.checkPW(Integer.parseInt(inputID.getText()), Integer.parseInt(inputPW.getText()));
            currentPanel.setVisible(false);
            stockManager.printStockList();
            printMainScene();
        });
    }

    void printMsgAndMainScene(String msg) {
        getContentPane().removeAll();
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JLabel label = new JLabel(msg);


        currentPanel.add(homeBtn);
        currentPanel.add(label);
        add(currentPanel);

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
    }

    void showErrorMessage(JLabel j) {
        j.setVisible(true);
    } 

    public void displayPaymentSuccess() {

    }
    public void displayInsufficientBalance() {

    }

    void showItems() {
        currentPanel = new JPanel();
        List<Item> items = new ArrayList<>();
        items.add(new Item(1000, 1 , "콜라"));
        items.add(new Item(1000, 2 , "사이다"));
        items.add(new Item(1000, 3 , "녹차"));
        items.add(new Item(1000, 4 , "홍차"));
        items.add(new Item(1000, 5 , "밀크티"));
        items.add(new Item(1000, 6 , "탄산수"));
        items.add(new Item(1000, 7 , "보리차"));
        items.add(new Item(1000, 8 , "캔커피"));
        items.add(new Item(1000,9, "물"));
        items.add(new Item(1000, 10 , "에너지드링크"));
        items.add(new Item(1000, 11 , "유자차"));
        items.add(new Item(1000, 12 , "식혜"));
        items.add(new Item(1000, 13 , "아이스티"));
        items.add(new Item(1000, 14 , "딸기주스"));
        items.add(new Item(1000, 15 , "오렌지주스"));
        items.add(new Item(1000, 16 , "포도주스"));
        items.add(new Item(1000, 17 , "이온음료"));
        items.add(new Item(1000, 18 , "아메리카노"));
        items.add(new Item(1000, 19 , "핫초코"));
        items.add(new Item(1000, 20 , "카페라떼"));

        getContentPane().removeAll();
        stockManager.printStockList();
        JPanel itemPanel = new JPanel();
        GridLayout gl= new GridLayout(5,4);
        JLabel errorMsg = new JLabel("Fail.");
        currentPanel = new JPanel();
        JButton homeBtn = new JButton("Home");
        JTextField inputItemCode = new JTextField(10);
        JTextField inputItemNum = new JTextField(10);
        JLabel totalPrice = new JLabel("total : ");
        JButton submitBtn = new JButton("Purchase");
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        itemPanel.setLayout(gl);
        errorMsg.setVisible(false);
        currentPanel.add(itemPanel);
        currentPanel.add(errorMsg);
        currentPanel.add(homeBtn);
        currentPanel.add(inputItemCode);
        currentPanel.add(inputItemNum);
        currentPanel.add(totalPrice);
        currentPanel.add(submitBtn);


        add(currentPanel);

        for(int i =0;i<20;i++){
            JTextField field = new JTextField(items.get(i).name+"("+items.get(i).code+")",10);
            field.setBorder(border);
            itemPanel.add(field);
        }

        homeBtn.addActionListener(e -> {
            currentPanel.setVisible(false);
            printMainScene();
        });
        submitBtn.addActionListener(e -> {
            boolean result = stockManager.selectStock(Integer.parseInt(inputItemCode.getText()), Integer.parseInt(inputItemNum.getText()));
            if (result) {
                currentPanel.setVisible(false);
                stockManager.printStockList();
                printMsgAndMainScene("good deal");
            } else {
                prepaymentManager.askStockRequest(Integer.parseInt(inputItemCode.getText()), Integer.parseInt(inputItemNum.getText()));
                currentPanel.setVisible(false);
                printMsgAndMainScene("fail");
            }

        });

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculate();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                calculate();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                calculate();
            }

            private void calculate() {
                try {
                    int itemCode = Integer.parseInt(inputItemCode.getText());
                    int itemNum = Integer.parseInt(inputItemNum.getText());
                    int total = items.get(itemCode - 1).price * itemNum;
                    totalPrice.setText("total : "+total);
                    errorMsg.setVisible(false);
                    revalidate();
                } catch (NumberFormatException e) {
                    totalPrice.setText("total : ");
                    errorMsg.setVisible(true);
                    revalidate();
                }
            }
        };

        inputItemCode.getDocument().addDocumentListener(documentListener);
        inputItemNum.getDocument().addDocumentListener(documentListener);


    }

}

