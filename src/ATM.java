import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ATM {

    Scanner in = new Scanner(System.in);
    String startOp, userOp;
    List<Account> accounts = new ArrayList<>();
    private Account user = null;

    void Start() {
        while (true) {
            startOrder();
            System.out.println("请输入你的选择：");
            startOp = in.next();
            if (Objects.equals(startOp, "1") && accounts.isEmpty()) {
                System.out.println("当前你还没有账户，请创建你的账户！");
                continue;
            }
            if (Objects.equals(startOp, "1") && !accounts.isEmpty()) {
                if (login()) {
                    UserSeriveimpl();
                }
            } else if (Objects.equals(startOp, "2")) {
                createUserAccount();
            } else {
                System.out.println("选择错误，请重新输入你的选择。");
            }

        }
    }

    void UserSeriveimpl() {

        while (true) {
            UserOrder();
            userOp = in.next();
            switch (userOp) {
                case "1":
                    consultUser();
                    break;
                case "2":
                    System.out.println("请输入你要存储的金额：");
                    double stockMoney = in.nextDouble();
                    stockCash(stockMoney);
                    break;
                case "3":
                    System.out.println("请输入你要取出的金额：");
                    double retrieveMoney = in.nextDouble();
                    retrieveCash(retrieveMoney);
                    break;
                case "4":
                    divertCahs();
                    break;
                case "5":
                    if (updatePassword()) {
                        user = null;
                        return;
                    } else break;
                case "6":
                    opRecord();
                    break;
                case "7": {
                    user = null;
                    System.out.println("退出成功！");
                    return;
                }
                case "8":
                    if (deleteUser()) {
                        user = null;
                        return;
                    } else break;
                default:
                    System.out.println("选择错误，请重新输入你的选择：");
            }
        }
    }

    void divertCahs() {
        System.out.println("请输入转账卡号：");
        String carID = in.next();
        System.out.println("请输入转账卡号用户名：");
        String userName = in.next();
        Account toUser = null;
        for (Account account : accounts) {
            if (account.getCarId().equals(carID) && account.getUsername().equals(userName)) {
                toUser = account;
                break;
            }
        }
        if (Objects.isNull(toUser)) {
            System.out.println("用户不存在或卡号错误！");
            return;
        } else {
            System.out.println("请输入转账金额：");
            double divertMoney = in.nextDouble();
            if (Double.compare(user.getStockCash(), divertMoney) >= 0) {
                user.setStockCash(-divertMoney);
                toUser.setStockCash(divertMoney);
                System.out.println("转账成功！");
                String date = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh时mm分").format(LocalDateTime.now());

                user.opList.add(date + " 转账给用户" + toUser.getUsername() + " 金额：" + divertMoney);
                toUser.opList.add(date + " 收到用户" + user.getUsername() + " 金额：" + divertMoney);
            }
            else{
                System.out.println("转账失败，余额不足！");
            }
        }
    }

    void consultUser() {
        System.out.println("用户名：" + user.getUsername());
        System.out.println("性别：" + user.getGender());
        System.out.println("开号：" + user.getCarId());
        System.out.println("余额：" + user.getStockCash());
        System.out.println("每次限额：" + user.getConfineCash());
        System.out.println("开户时间： " + user.getCreatTime());
    }

    void stockCash(double money) {

        user.setStockCash(money);
        System.out.println("存储成功！");
        String date = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh时mm分").format(LocalDateTime.now());

        user.opList.add(date + " 存储金额：" + money);
    }

    void retrieveCash(double money) {
        if (Double.compare(user.getStockCash(), money) >= 0
                && money <= Double.parseDouble(user.getConfineCash())) {
            user.setStockCash(-money);
            System.out.println("取出成功！");
            String date = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh时mm分").format(LocalDateTime.now());

            user.opList.add(date + " 取出金额：" + money);
        } else if (Double.compare(user.getStockCash(), money) == -1) {
            System.out.println("余额不足！");
        } else if (money > Double.parseDouble(user.getConfineCash())) {
            System.out.println("每次取出的金额不超过" + user.getConfineCash());
        }
    }

    boolean login() {
        System.out.println("请输入你的卡号：");
        String carId = in.next();
        System.out.println("请输入你的密码：");
        String password = in.next();
        for (Account account : accounts) {
            if (account.getPassWord().equals(password) && account.getCarId().equals(carId)) {
                user = account;
                break;
            }
        }

        if (Objects.isNull(user) || !user.getPassWord().equals(password) || !user.getCarId().equals(carId)) {
            System.out.println("卡号或密码错误，请重新输入");
            return false;
        } else {
            System.out.println("登陆成功，欢迎 " + user.getUsername());
            return true;
        }
    }

    boolean updatePassword() {
        System.out.println("请输入你的当前密码：");
        String currentPassword = in.next();
        if (user.getPassWord().equals(currentPassword)) {
            System.out.println("请输入你要修改的密码：(6位密码)");
            String updatePassword = in.next();
            System.out.println("请确认密码：");
            String comfiredPassword = in.next();
            if (updatePassword.length() != 6 || !updatePassword.equals(comfiredPassword)) {
                System.out.println("修改失败，请按要求修改密码！");
            } else {
                user.setPassWord(updatePassword);
                System.out.println("密码修改成功！请重新登录");
                return true;
            }
        } else {
            System.out.println("密码错误！");
        }
        return false;
    }

    void createUserAccount() {
        String username;
        String passWord;
        String comfirmedPassword;
        String confineCash;
        char gender;
        System.out.println("请输入你的用户名：（不超过五位且不能为空）");
        username = in.next();
        System.out.println("请输入你的性别：（男/女）");
        gender = in.next().charAt(0);
        System.out.println("请输入你的密码：（设置6位密码）");
        passWord = in.next();
        System.out.println("请确认你的密码：");
        comfirmedPassword = in.next();
        System.out.println("请输入一次限定金额：（请设置为正整数）");
        confineCash = in.next();
        if (username == null || username.isEmpty() || username.length() > 5
                || (gender != '男' && gender != '女')
                || passWord == null || passWord.length() != 6 || !comfirmedPassword.equals(passWord)
                || confineCash.contains(".") || confineCash.charAt(0) == '-') {
            System.out.println("创建失败，请按要求重新创建用户！");
            return;
        }

        Account user = new Account(username, passWord, comfirmedPassword, confineCash, gender);
        accounts.add(user);
        System.out.println("创建成功，你的卡号是：" + user.getCarId());

    }

    boolean deleteUser() {
        if (user.getStockCash() > 0) {
            System.out.println("注销失败！该账户存储余额为：" + user.getStockCash());
        } else {
            System.out.println("请确定是否删除该用户 Y/N");
            String op = in.next();
            if (Objects.equals("Y", op)) {
                if (accounts.remove(user)) {
                    System.out.println("注销成功！");
                    return true;
                } else System.out.println("注销失败！");
            } else if (Objects.equals("N", op)) {
                System.out.println("注销失败！");
            }
        }
        return false;
    }

    void opRecord(){
        user.opList.forEach(System.out::println);
    }

    void startOrder() {
        System.out.println("*************Welcome to BankService***********");
        System.out.println("****************1.用户登录**********************");
        System.out.println("****************2.用户开户**********************");
        System.out.println("**********************************************");
    }

    void UserOrder() {
        System.out.println("*****************1.查询用户**********************");
        System.out.println("*****************2.存款   **********************");
        System.out.println("*****************3.取款   **********************");
        System.out.println("*****************4.转账   **********************");
        System.out.println("*****************5.修改密码**********************");
        System.out.println("*****************6.操作记录**********************");
        System.out.println("*****************7.退出   **********************");
        System.out.println("*****************8.注销账户**********************");
        if (Objects.isNull(user)) System.out.println("**********************************************");
        else System.out.println("****************Welcome! " + user.getUsername() + "**********************");
        System.out.println("请输入你的选择：");
    }
}
