package http;

/**
 * Created by tales on 4/09/15.
 */
public class ResultCallback {
    private boolean valido; // mandatory;
    private String message ; // optional

    private ResultCallback(Builder builder){
        this.valido = builder.valido;
        this.message = builder.message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public static class Builder{
        private boolean valido;
        private String message;

        public Builder(boolean valido){
            this.valido = valido;
        }

        public ResultCallback build(){
            return new ResultCallback(this);
        }

        public Builder valido(boolean value){
            this.valido = value;
            return this;
        }
        public Builder message(String message){
            this.message = message;
            return this;
        }


    }
}
