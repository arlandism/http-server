package mocks;

import com.arlandis.RequestInterface;
import com.arlandis.ResponseBuilderInterface;

/**
 * Created with IntelliJ IDEA.
 * User: arlandislawrence
 * Date: 10/10/13
 * Time: 2:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class MockResponseBuilder implements ResponseBuilderInterface {
    @Override
    public String generateResponse(RequestInterface request) {
        return "bar";
    }
}
