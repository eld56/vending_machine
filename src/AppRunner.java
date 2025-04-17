import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private  final CardReaceiver cardReaceiver;

    private  final CoinAcceptor coinAcceptor;

    private  int actionU;
    private  int actionU2;
    private  String userChoose;

    private static boolean isExit = false;
    static Scanner sc = new Scanner(System.in);

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        cardReaceiver = new CardReaceiver(100);
        coinAcceptor = new CoinAcceptor(100);
        System.out.println("Выбирите способ оплаты: card/coin:");
        boolean isChoose = true;
       while (isChoose){
           userChoose = sc.nextLine();

           if(userChoose.isBlank()){
               System.out.println("Выбирите способ оплаты: card/coin:");
               userChoose = sc.nextLine();
           }else {
               switch (userChoose) {
                   case "card":
                       actionU = cardReaceiver.getMoney();
                       isChoose = false;
                       break;
                   case "coin":
                       actionU = coinAcceptor.getAmount();
                       isChoose = false;
                       break;
                   default:
                       System.out.println("not founded action!");
               }
           }

       }
    }

    public static void run() {
        AppRunner app = new AppRunner();

        while(!isExit) {
//
            app.startSimulation();

        }


    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        if(userChoose.equals("card")){
        print("Деньги на сумму: "  + cardReaceiver.getMoney());

        }else if(userChoose.equals("coin")){
        print("Монет на сумму: "  + coinAcceptor.getAmount());

        }

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (actionU >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().substring(0, 1);
        if ("a".equalsIgnoreCase(action)) {
            if(userChoose.equals("card")){
                System.out.println("Введите сумму пополнения");
                int userPay = sc.nextInt();
                cardReaceiver.setMoney(cardReaceiver.getMoney() + userPay);
                print("Вы пополнили баланс на " + userPay);
            }else if(userChoose.equals("coin")){
                coinAcceptor.setAmount(coinAcceptor.getAmount() + 10);
                print("Вы пополнили баланс на 10");
            }

            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    if(userChoose.equals("card")){
                        cardReaceiver.setMoney(cardReaceiver.getMoney() - products.get(i).getPrice());

                    } else if(userChoose.equals("coin")){
                        coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());

                    }
                    print("Вы купили " + products.get(i).getName());
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попрбуйте еще раз.");
                chooseAction(products);
            }
        }


    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }
    private String fromConsole() {
            return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
