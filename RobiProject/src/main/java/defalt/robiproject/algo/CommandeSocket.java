package defalt.robiproject.algo;

public class CommandeSocket {
    public String name;
    public String code;
    public  CommandeSocket(String name,String code){
        this.name=name;
        this.code=code;
    }
    public  CommandeSocket(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
