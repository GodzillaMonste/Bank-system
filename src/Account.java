import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Account {
    private String username;
    private String passWord;
    private String comfirmedPassword;

    private String confineCash;
    private char gender;

    private Double stockCash;

    private String carId;

    Random random = new Random();

    String creatTime;

     List<String> opList = new ArrayList<>();



    public Account(String username, String passWord, String comfirmedPassword, String confineCash, char gender) {
        this.username = username;
        this.passWord = passWord;
        this.comfirmedPassword = comfirmedPassword;
        this.confineCash = confineCash;
        this.gender = gender;
        this.stockCash = 0.00;
        this.carId =  (random.nextInt(90000000) + 10000000) + "";
        this.creatTime = DateTimeFormatter.ofPattern("yyyy年MM月dd日").format(LocalDateTime.now());
    }

    public String getCreatTime() {
        return creatTime;
    }
    public String getUsername() {
        return username;
    }


    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public String getConfineCash() {
        return confineCash;
    }

    public void setConfineCash(String confineCash) {
        this.confineCash = confineCash;
    }

    public char getGender() {
        return gender;
    }

    public Double getStockCash() {
        return stockCash;
    }

    public void setStockCash(Double stockCash) {
        this.stockCash += stockCash;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}