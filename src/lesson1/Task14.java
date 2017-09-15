package lesson1;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Objects;

/**
 * There is a class Request(data: String) for representing some XML requests in a payment system. The class has a getter method getData() and a constructor with one argument (data). See below.

 Typically, the value of data looks like shown below (in a human-readable format, in processing it's a single line string).

 <request>
 <transaction>
 <type>payment</type>
 <sum>100000</sum>
 <order_id>e94dc619-6172-4ffe-aae8-63112c551570</order>
 <desc>We'd like to buy an elephant</desc>
 </transaction>
 <digest>CZVMYTgc3iiOdJjFP+6dhQ==</digest>
 </request>

 You should to write the chain of responsibility pattern in functional style for stage-by-stage request creating.
 For this:
 1) write a functional interface RequestHandler with a single abstract method handle and a default method; the first is needed for using lambda expressions and should accept a Request and returns new Request with changed data, the second is for combining several handlers into the one.
 2) create a commonRequestHandler combined from three existing handlers: wrapInTransactionTag, createDigest and wrapInRequestTag.

 Important. Use the provided template for your solution. Do not change it and read comments in the code. The template is long enough, scroll it to the bottom. All comments where your code is expected start with !!!

 In the next step, you can read how the requests should be created. Keep in mind, all handlers with logic are already implemented and available for you. You need to write only a suitable functional interface and compose all existing handlers as functions into one commonRequestHandler to follow the described algorithm.
 */
public class Task14 {

    public static void main(String[] args) {
        final String inputData = "<request>\n" +
                "  <transaction>\n" +
                "    <type>payment</type>\n" +
                "    <sum>100000</sum>\n" +
                "    <order_id>e94dc619-6172-4ffe-aae8-63112c551570</order>\n" +
                "    <desc>We'd like to buy an elephant</desc>\n" +
                "  </transaction>\n" +
                "  <digest>CZVMYTgc3iiOdJjFP+6dhQ==</digest>\n" +
                "</request>";

      //  IntUnaryOperator

        System.out.println(commonRequestHandler.handle(new Request("")));

    }

    @FunctionalInterface
    interface RequestHandler {


        // !!! write a method handle that accept request and returns new request here
        // it allows to use lambda expressions for creating handlers below
          Request handle(Request request);

        // !!! write a default method for combining this and other handler single one
        // the order of execution may be any but you need to consider it when composing handlers
        // the method may has any name
        default RequestHandler setSuccessor(RequestHandler requestHandler ){
            Objects.requireNonNull(requestHandler);
            return (Request i)  -> handle(requestHandler.handle(i));
        }

    }

    /**
     * Accepts a request and returns new request with data wrapped in the tag <transaction>...</transaction>
     */
    final static RequestHandler wrapInTransactionTag = (req) -> {
        System.out.println("wrapInTransactionTag");
        return new Request(String.format("<transaction>%s</transaction>", req.getData()));
    };

    /**
     * Accepts a request and returns a new request with calculated digest inside the tag <digest>...</digest>
     */
    final static RequestHandler createDigest =  (req) -> {
        System.out.println("createDigest");
        String digest = "";
                try {
                    final MessageDigest md5 = MessageDigest.getInstance("MD5");
                    final byte[] digestBytes = md5.digest(req.getData().getBytes("UTF-8"));
                    digest = new String(Base64.getEncoder().encode(digestBytes));
                } catch (Exception ignored) { }
                return new Request(req.getData() + String.format("<digest>%s</digest>", digest));
            };

    /**
     * Accepts a request and returns a new request with data wrapped in the tag <request>...</request>
     */
    final static RequestHandler wrapInRequestTag =  (req) ->
    {
        System.out.println("wrapInRequestTag");
        return new Request(String.format("<request>%s</request>", req.getData()));
    };



    /**
     * It should represents a chain of responsibility combined from another handlers.
     * The format: commonRequestHandler = handler1.setSuccessor(handler2.setSuccessor(...))
     * The combining method setSuccessor may has another name
     */
    public final static RequestHandler commonRequestHandler = wrapInRequestTag.setSuccessor(createDigest.setSuccessor(wrapInTransactionTag));
            //wrapInTransactionTag.setSuccessor(createDigest.setSuccessor(wrapInRequestTag)); // !!! write the combining of existing handlers here

    /**
     * Immutable class for representing requests.
     * If you need to change the request data then create new request.
     */
    static class Request {
        private final String data;

        public Request(String requestData) {
            this.data = requestData;
        }

        public String getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Request{" +
                    "data='" + data + '\'' +
                    '}';
        }
    }
}

