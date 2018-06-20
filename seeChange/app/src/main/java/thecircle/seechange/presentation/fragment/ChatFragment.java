package thecircle.seechange.presentation.fragment;


import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import io.socket.engineio.client.transports.WebSocket;


import org.java_websocket.WebSocketFactory;
import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import javax.net.ssl.SSLContext;



import thecircle.seechange.R;
import thecircle.seechange.domain.Constants;
import thecircle.seechange.domain.Message;

import static io.antmedia.android.broadcaster.security.HashGenerator.bin2hex;

public class ChatFragment extends Fragment {
    private WebSocketClient mWebSocketClient;
    private ImageButton send_button;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mMessagesView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        connectWebSocket("http://145.49.24.24:3000");
        connectWebSocket("http://the-circle-chat.herokuapp.com");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

//        addLog(getResources().getString(R.string.message_welcome));
        final EditText message_input = (EditText) view.findViewById(R.id.message_input);
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendMessage(message_input.getText().toString());
                    message_input.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (SignatureException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;


    }

    private Socket mSocket;
    private void connectWebSocket(String websocketEndPointUrl){
//
//        URI uri;
//        try {
//            Log.i("Uri try and catch", "Does it come here?");
////            mSocket = IO.socket("https://the-circle-chat.herokuapp.com");
////            mSocket.connect();
////            websocketEndPointUrl="ws://the-circle-chat.herokuapp.com/";
//            websocketEndPointUrl="ws://145.49.24.24:3000";
//
//            uri = new URI(websocketEndPointUrl);
//            //uri = new URI("ws://the-circle-chat.herokuapp.com");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return;
//        }
//
//        mWebSocketClient = new WebSocketClient(uri, new Draft_10()) {
//            @Override
//            public void onOpen(ServerHandshake serverHandshake) {
//                Log.i("Websocket", "Opened");
//                mWebSocketClient.onMessage("message");
//                mWebSocketClient.emit("connection_info");
//            }
//
//            @Override
//            public void onMessage(String s) {
//                final String message = s;
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        TextView textView = (TextView) getView().findViewById(R.id.messages);
//                        textView.setText(textView.getText() + "\n" + message);
//                    }
//                });
//            }
//
//            @Override
//            public void onClose(int i, String s, boolean b) {
//                Log.i("Websocket", "Closed " + s);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.i("Websocket", "Error " + e.getMessage());
//            }
//        };
////        if (websocketEndPointUrl.indexOf("ws") == 0)
////        {
////            try {
////                SSLContext sslContext = SSLContext.getDefault();
////                mWebSocketClient.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sslContext));
////            } catch (NoSuchAlgorithmException e) {
////                e.printStackTrace();
////            }
////        }
//        mWebSocketClient.connect();
        try{
            IO.Options opt = new IO.Options();
            opt.transports = new String[] {WebSocket.NAME};
            opt.secure = false;
            mSocket = IO.socket(websocketEndPointUrl, opt);
            mSocket.on("connect", onConnection);
            mSocket.on("connection_info", onConnectionInfo);
            mSocket.on("room_joined", roomJoined);
            mSocket.on("verified", verified);
            mSocket.on("message", onMessage);

            mSocket.connect();
        }catch(Exception E){
            E.printStackTrace();
            Log.i("ERROR", E.toString());
        }

    }

    private Emitter.Listener onConnection = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SharedPreferences prefs = getContext().getSharedPreferences("CREDENTIALS", getContext().MODE_PRIVATE);
                        String crt = prefs.getString("crt", null);
                        JSONObject object = new JSONObject("{ \"certificate\" : \"" + crt + "\"}");
                        mSocket.emit("verify_identity",  object);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onConnectionInfo = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i("CONINFO", data.toString());
                    Log.i("CONINFO", "test");
                }
            });
        }
    };

    private Emitter.Listener roomJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];

                    Log.i("ROOMJOIN", data.toString());
                }
            });
        }
    };

    private Emitter.Listener verified = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Boolean data = (Boolean) args[0];
                    SharedPreferences prefs = getContext().getSharedPreferences("CREDENTIALS", getContext().MODE_PRIVATE);
                    String username = prefs.getString("username", null);
                    mSocket.emit("join_room", username);
                }
            });
        }
    };

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i("MESSAGE", data.toString());

                }
            });
        }
    };
    private void addLog(String message) {
        mMessages.add(new Message.Builder(Message.TYPE_LOG)
                .message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void sendMessage(String message) throws JSONException, InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        SharedPreferences prefs = getContext().getSharedPreferences("CREDENTIALS", getContext().MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String crt = prefs.getString("crt", null);
        long unixTime = System.currentTimeMillis() / 1000L;

        JSONObject data = new JSONObject();
        data.put("room", username);
        data.put("timestamp", unixTime);
        data.put("content", message);
        data.put("certificate", crt);
        data.put("signature", sign(message + unixTime));
        mSocket.emit("message", data);
    }

    public String sign(String message) throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException {
        MessageDigest digest = null;
        SharedPreferences prefs = getContext().getSharedPreferences("CREDENTIALS", getContext().MODE_PRIVATE);
        String privateKeyString = prefs.getString("privatekey", "unavailabe");

        privateKeyString = privateKeyString.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        privateKeyString = privateKeyString.replace("-----END RSA PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] secret = Base64.decode(privateKeyString, Base64.NO_PADDING);
        RSAPrivateKey privateKeyObject = (RSAPrivateKey) kf.generatePrivate(new PKCS8EncodedKeySpec(secret));


        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] datahashed = digest.digest(message.getBytes());
        String hexhash = bin2hex(datahashed);

        Signature sig = Signature.getInstance("SHA256WithRSA");
        sig.initSign(privateKeyObject);
        sig.update(hexhash.getBytes());
        byte[] signedData = sig.sign();

        Formatter formatter = new Formatter();
        for (byte b : signedData) {
            formatter.format("%02x", b);
        }
        String charactersToSend = formatter.toString();

        return charactersToSend;
    }

}