package defalt.robiproject.algo;

public class CommandeSocket {
    private String name;
    private String code;
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
