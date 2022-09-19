package top.mikevane.laji.exception;

/**
 * 针对客户端的异常类
 * @Author mikevane
 * @Date 17:51
 */
public class ClientException extends Exception{
    private String message;

    public ClientException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "message='" + this.getMessage() + '\'' +
                '}';
    }
}
