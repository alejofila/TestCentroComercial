package http;

/**
 * Created by tales on 4/09/15.
 */
public interface CallbackLogin extends Callback{
    @Override
    void receiveResult(ResultCallback result);
}
