package  com.example.i78k.monitor.soap;

import android.util.Pair;

import com.example.i78k.monitor.BuildConfig;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

public class WebServiceCommunication {

    final private String User = BuildConfig.SERVER_LOGIN;
    final private String Pass = BuildConfig.SERVER_PASSWORD;

    final private String NAMESPACE = BuildConfig.SERVER_NAMESPACE;
    final private String URL = BuildConfig.SERVER_URL;

    public Pair<Boolean, String> login(String login, String password) {
        final String SOAP_ACTION = "Authorization";
        final String METHOD_NAME = "Authorization";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("Login", login);
        request.addProperty("Password", password);

        SoapSerializationEnvelope envelope = getEnvelope(request);

        HttpTransportSE androidHttpTransport = new HttpTransportBasicAuthSE(URL, User, Pass);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Vector response = (Vector) envelope.getResponse();
            if (response.get(1) == null) {
                // no error info, it means success
                return new Pair<>(true, response.get(0).toString());
            } else {
                return new Pair<>(false, response.get(1).toString());
            }
        } catch (Exception e) {
            return new Pair<>(false, e.getMessage());
        }
    }

    private SoapSerializationEnvelope getEnvelope(SoapObject request){
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet=true;
        envelope.setOutputSoapObject(request);
        return envelope;
    }

    public Pair<Boolean, String> busyConference(String id) {
        final String SOAP_ACTION = "BusyConference";
        final String METHOD_NAME = "BusyConference";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("Id", id);

        SoapSerializationEnvelope envelope = getEnvelope(request);

        HttpTransportSE androidHttpTransport = new HttpTransportBasicAuthSE(URL, User, Pass);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Vector response = (Vector) envelope.getResponse();
            if (response.get(1) == null) {
                // no error info, it means success
                return new Pair<>(true, response.get(0).toString());
            } else {
                return new Pair<>(false, response.get(1).toString());
            }
        } catch (Exception e) {
            return new Pair<>(false, e.getMessage());
        }
    }
//    <xs:element name="Id" type="xs:string"/>
//    <xs:element name="IdConference" type="xs:string"/>
//    <xs:element name="BeginTime" type="xs:dateTime"/>
//    <xs:element name="EndTime" type="xs:dateTime"/>
//    <xs:element name="Comment" type="xs:string"/>
    public Pair<Boolean, String> reservation(String id, String idConference, String beginTime, String endTime, String comment) {
        final String SOAP_ACTION = "Reservation";
        final String METHOD_NAME = "Reservation";

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        request.addProperty("Id", id);
        request.addProperty("IdConference", idConference);
        request.addProperty("BeginTime", beginTime);
        request.addProperty("EndTime", endTime);
        request.addProperty("Comment", comment);

        SoapSerializationEnvelope envelope = getEnvelope(request);

        HttpTransportSE androidHttpTransport = new HttpTransportBasicAuthSE(URL, User, Pass);

        try {
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Vector response = (Vector) envelope.getResponse();
            if (response.get(1) == null) {
                // no error info, it means success
                return new Pair<>(true, "Бронирование успешно завершено");
            } else {
                return new Pair<>(false, response.get(1).toString());
            }
        } catch (Exception e) {
            return new Pair<>(false, e.getMessage());
        }
    }

}
