// depends on js-constants.html, jquery

/**
 * Untuk penjelasan lebih lengkap, baca DOCUMENTATION.md pada microservice
 */


/**
 * @abstract
 */
class AbstractStompWebsocketEventObserver{
    constructor() {
        if (this.constructor === AbstractStompWebsocketEventObserver) {
            throw new Error("Can't instantiate abstract class!");
        }
    }

    /**
     * Mengecek apakah observer ini merupakan observer yang sesuai untuk menangani suatu event-type
     * @abstract
     * @param eventType {string} string
     * @return {boolean} true apabila observer ini bertujuan untuk menangani event-type tersebut
     */
    isAllowedToHandle(eventType){
        throw new Error("method not implemented");
    }

    /**
     * @abstract
     *
     * Menangani suatu event (message) yang dikirim oleh server
     * @param messageBody {any} konten pesan yang dikirim oleh server. Tipe data-nya akan bervariasi,
     * tergantung data yang dikirim oleh server
     *
     * @param eventType {string} tipe event yang dikirim oleh server. Misal "score-update" atau "restart-vote"
     */
    handle(messageBody, eventType){
        throw new Error("method not implemented");
    }

    /**
     * @param observee {StompWebsocketCommunication}
     */
    observe(observee){
        observee.addObserver(this);
    }
}


/**
 * The client that will be observed
 */
class StompWebsocketCommunication{
    #observers = [];
    #onConnectEventHandlers = [];
    #onErrorEventHandlers = [];
    #onDisconnectEventHandlers = [];

    constructor(websocketAddress, defaultSendAddr, subscribeAddress, playerId, sessionToken) {
        if (this.stompClient != null)
            this.stompClient.disconnect();

        this.playerId = playerId.toString();
        this.sessionToken = sessionToken.toString();

        this.socket = new SockJS(websocketAddress);
        this.defaultSendAddr = defaultSendAddr;
        this.subscribeAddress = subscribeAddress;
        this.stompClient = Stomp.over(this.socket);
        this.stompClient.reconnect_delay = 4000;

        this.connectCallback = (frame) => {
            this._onConnect(frame);
            this.stompClient.subscribe(this.subscribeAddress,
                (data) => this._subscribeEventHandler(data));
        };

        this.errorCallback = (frame) => this._onError(frame);
        return this;
    }

    /**
     * Mengirimkan suatu message (atau bisa disebut 'event') ke main-server dengan type tertentu.
     * Baca DOCUMENTATION.md untuk keterangan lebih lanjut
     *
     * @param type {String} merepresentasikan event/message type. Menjelaskan tentang apa yang ingin dilakukan oleh msg ini
     * @param messageData {any} data yang ingin dikirim
     * @param [sendAddr] Alamat yang dituju. Jika diabaikan, akan menggunakan alamat yang diberikan pada constructor
     * @param [header] header pesan yang ingin dikirim
     */
    send(type, messageData, sendAddr=null, header={}){
        var toBeSent = {
            'type': type,
            'body': messageData,
            'playerId': this.playerId,
            'sessionToken': this.sessionToken
        };
        var json = JSON.stringify(toBeSent);

        this.sendRaw(json, sendAddr, header);
    };

    /**
     * Mengecek apakah websocket sudah terhubung ke server atau belum
     * @return {boolean} true apabila sudah terhubung
     */
    isConnected(){
        return this.stompClient.connected;
    }

    sendRaw(messageBody, sendAddr=null, header={}){
        if (sendAddr == null){
            sendAddr = this.defaultSendAddr;
        }

        this.stompClient.send(sendAddr, header, messageBody);
    };


    connect(onConnectHeader = {}){
        this.stompClient.connect(onConnectHeader,
            this.connectCallback,
            this.errorCallback);
        return this;
    }

    disconnect(){
        this.stompClient.disconnect();
        return this;
    }

    addOnConnectEventHandler(func){
        this.#onConnectEventHandlers.push(func);
        if (this.isConnected())
            func()
        return this;
    }

    addOnErrorEventHandler(func){
        this.#onErrorEventHandlers.push(func);
        return this;
    }

    addOnDisconnectEventHandler(func){
        this.#onDisconnectEventHandlers.push(func);
        return this;
    }

    addObserver(observer){
        console.assert(observer instanceof AbstractStompWebsocketEventObserver);
        this.#observers.push(observer);
    }


    _subscribeEventHandler(wholeData){
        var rawData = wholeData.body;
        var parsedRawData = JSON.parse(rawData);
        console.assert("type" in parsedRawData);
        console.assert("body" in parsedRawData);

        var type = parsedRawData["type"];
        var body = parsedRawData["body"];
        for (var i=0; i < this.#observers.length; i++){
            var observer = this.#observers[i];
            if (observer.isAllowedToHandle(type))
                observer.handle(body, type);
        }
    }


    _onConnect(frame){
        for (let i = 0; i < this.#onConnectEventHandlers.length; i++) {
            var eventHandler = this.#onConnectEventHandlers[i];
            console.log(eventHandler);
            eventHandler(frame);
        }
    }

    _onError(frame){
        for (let i = 0; i < this.#onErrorEventHandlers.length; i++) {
            var eventHandler = this.#onErrorEventHandlers[i];
            eventHandler(frame);
        }
    }

    _onDisconnect(closeEvent){
        for (let i = 0; i < this.#onDisconnectEventHandlers.length; i++) {
            var eventHandler = this.#onDisconnectEventHandlers[i];
            eventHandler(closeEvent);
        }
    }
}


class HeartBeatStompWebsocketCommunication extends StompWebsocketCommunication{
    #heartbeatAddr = "";
    #timeoutId = null;

    constructor(websocketAddress, defaultSendAddr, subscribeAddress, heartBeatAddr, playerId, sessionToken) {
        super(websocketAddress, defaultSendAddr, subscribeAddress, playerId, sessionToken);
        this.#heartbeatAddr = heartBeatAddr;
        this._resetHeartbeatInterval(5000);
    }


    sendRaw(messageBody, sendAddr=null, header={}){
        super.sendRaw(messageBody, sendAddr, header);
        this._resetHeartbeatInterval(5000);
    }

    sendHeartBeat(){
        var data = {
            'playerId': this.playerId,
            'sessionToken': this.sessionToken
        };
        var json = JSON.stringify(data);
        this._resetHeartbeatInterval(5000);
        super.sendRaw(json, this.#heartbeatAddr);
    }

    _resetHeartbeatInterval(intervalMilliSecond){
        if (this.#timeoutId != null)
            clearTimeout(this.#timeoutId);
        this.#timeoutId = setTimeout(() => this.sendHeartBeat(), intervalMilliSecond);
    }
}




/**
 * Null apabila session belum terdefinisi (alias belum daftar sebagai host maupun guest).
 * Jika tidak, variabel ini akan berisi objek yang berfungsi untuk berkomunikasi dengan server
 * @type {StompWebsocketCommunication|null}
 */
var communicationProtocol = null;



var cookies = Cookies.withConverter({
    read: function (value, name) {
        return decodeURIComponent(value.replace(/\+/g, "%20"));
    }
})


var playerId = cookies.get("playerId");
var roomCode = cookies.get("roomCode");
var sessionToken = cookies.get("sessionToken");


if (sessionToken != null){
    communicationProtocol = new HeartBeatStompWebsocketCommunication(
        WEBSOCKET_SERVER_ADDR + "anagram-margana-websocket",
        "/from-client",
        "/subscribe/" +playerId+ "/" +sessionToken,
        "/heartbeat",
        playerId, sessionToken
    );

    communicationProtocol.addOnConnectEventHandler(
        () => {
            // send init message
            var data = {
                'roomCode': roomCode,
                'playerId': playerId,
                'sessionToken': sessionToken
            };
            communicationProtocol.sendRaw(JSON.stringify(data), "/init");
        }
    );

    communicationProtocol.connect();
}


if (!DEBUG){
    stompClient.debug = () => {};
}
