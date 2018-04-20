'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var diceNumbers = [];

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(loggedUser) {
    username = loggedUser;
    console.log(username);
    if(username) {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    var init = 0;
    if (init === 0){
        for(var i = 0; i<document.getElementsByClassName("dice").length; i++){
            var n = rand(0,6);
            var d = document.getElementsByClassName("dice")[i];
            dice(n, d);
        }
        init += 1;
    }
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
    else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
        textElement = document.createElement('p');
        messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
    else if(message.type === 'SCORE'){
        var records = document.getElementsByClassName(message.sender);
        console.log(records);
        var content = message.content.split(",");
        var combo = content[0];
        var resultSum = content[1];
        var order;
        switch (combo) {
            case "aces": order = 0;
                break;
            case "twos": order = 1;
                break;
            case "threes": order = 2;
                break;
            case "fours": order = 3;
                break;
            case "fives": order = 4;
                break;
            case "sixes": order = 5;
                break;
            case "threekind": order = 8;
                break;
            case "fourkind": order = 9;
                break;
            case "full": order = 10;
                break;
            case "small": order = 11;
                break;
            case "large": order = 12;
                break;
            case "chance": order = 13;
                break;
            case "yahtzee": order = 14;
                break;
        }
        records[order].innerHTML = resultSum;
    }
    else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        textElement = document.createElement('p');
        messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }


}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}
messageForm.addEventListener('submit', sendMessage, true);

function rand(min,max) {
    return Math.floor(Math.random()*(max-min+1)+min);
}

function dice(num, element){
    switch(num){
        case 0:element.innerHTML = '<input class="diceNumber" type="hidden" value="1"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10Z" /></svg>';break;
        case 1:element.innerHTML = '<input class="diceNumber" type="hidden" value="2"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15Z" /></svg>';break;
        case 2:element.innerHTML = '<input class="diceNumber" type="hidden" value="3"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15Z" /></svg>';break;
        case 3:element.innerHTML = '<input class="diceNumber" type="hidden" value="4"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15M17,5A2,2 0 0,0 15,7A2,2 0 0,0 17,9A2,2 0 0,0 19,7A2,2 0 0,0 17,5M7,15A2,2 0 0,0 5,17A2,2 0 0,0 7,19A2,2 0 0,0 9,17A2,2 0 0,0 7,15Z" /></svg>';break;
        case 4:element.innerHTML = '<input class="diceNumber" type="hidden" value="5"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15M17,5A2,2 0 0,0 15,7A2,2 0 0,0 17,9A2,2 0 0,0 19,7A2,2 0 0,0 17,5M12,10A2,2 0 0,0 10,12A2,2 0 0,0 12,14A2,2 0 0,0 14,12A2,2 0 0,0 12,10M7,15A2,2 0 0,0 5,17A2,2 0 0,0 7,19A2,2 0 0,0 9,17A2,2 0 0,0 7,15Z" /></svg>';break;
        case 5:element.innerHTML = '<input class="diceNumber" type="hidden" value="6"/><svg  viewBox="0 0 24 24"><path fill="#fff" d="M5,3H19A2,2 0 0,1 21,5V19A2,2 0 0,1 19,21H5A2,2 0 0,1 3,19V5A2,2 0 0,1 5,3M7,5A2,2 0 0,0 5,7A2,2 0 0,0 7,9A2,2 0 0,0 9,7A2,2 0 0,0 7,5M17,15A2,2 0 0,0 15,17A2,2 0 0,0 17,19A2,2 0 0,0 19,17A2,2 0 0,0 17,15M17,10A2,2 0 0,0 15,12A2,2 0 0,0 17,14A2,2 0 0,0 19,12A2,2 0 0,0 17,10M17,5A2,2 0 0,0 15,7A2,2 0 0,0 17,9A2,2 0 0,0 19,7A2,2 0 0,0 17,5M7,10A2,2 0 0,0 5,12A2,2 0 0,0 7,14A2,2 0 0,0 9,12A2,2 0 0,0 7,10M7,15A2,2 0 0,0 5,17A2,2 0 0,0 7,19A2,2 0 0,0 9,17A2,2 0 0,0 7,15Z" /></svg>';break;
        default:break;
    }
}

document.getElementById("rollDice").onclick = function(){
    var $ = document.getElementsByClassName("dice");
    for(var i = 0; i<$.length; i++){
        var num = rand(0,5);
        dice(num, $[i]);
        var x = $[i].childNodes[1];
        x.setAttribute("class", "animate");
        setTimeout(function(){x.removeAttribute("class")}, 500);
    }

};

function keepDie(order){
    document.getElementById("die"+order).classList.toggle("dice");
}

function saveCombination(input){
    var result = 0;
    diceNumbers = [];
    for(var i = 0; i<document.getElementsByClassName("diceNumber").length; i++){
        console.log(document.getElementsByClassName("diceNumber")[i].value);
        diceNumbers.push(parseInt(document.getElementsByClassName("diceNumber")[i].value));
    }
    console.log(diceNumbers);
    switch (input) {
        case "aces":
            result = getNumberResult(1);
            break;
        case "twos":
            result = getNumberResult(2);
            break;
        case "threes":
            result = getNumberResult(3);
            break;
        case "fours":
            result = getNumberResult(4);
            break;
        case "fives":
            result = getNumberResult(5);
            break;
        case "sixes":
            result = getNumberResult(6);
            break;
        case "threekind":
            result = getKind(3);
            break;
        case "fourkind":
            result = getKind(4);
            break;
        case "full":
            result = getFull();
            break;
        case "small":
            score.setSmall(result);
            break;
        case "large":
            score.setLarge(result);
            break;
        case "chance":
            score.setChance(result);
            break;
        case "yahtzee":
            result = getKind(5);
            break;
    }
    var messageContent = input + "," + result;
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageContent,
            type: 'SCORE'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    }
}

function getNumberResult(number){
    var result = 0;
    for(var i = 0; i<diceNumbers.length; i++){
        if(diceNumbers[i] === number){
            result += diceNumbers[i];
        }
    }
    return result;
}

function getKind(number){
    var result = 0;
    var dict = {};
    dict[1] = 0;
    dict[2] = 0;
    dict[3] = 0;
    dict[4] = 0;
    dict[5] = 0;
    dict[6] = 0;
    for(var i = 0; i<diceNumbers.length; i++){
        dict[diceNumbers[i]] += 1;
    }
    for(var key in dict){
        if(dict[key] === number){
            result = number*key;
        }
    }
    return result;
}

function getFull(){
    var result = 0;
    var dict = {};
    var three = false;
    var two = false;
    var used = 0;
    dict[1] = 0;
    dict[2] = 0;
    dict[3] = 0;
    dict[4] = 0;
    dict[5] = 0;
    dict[6] = 0;
    for(var i = 0; i<diceNumbers.length; i++){
        dict[diceNumbers[i]] += 1;
    }
    for(var key in dict){
        if(dict[key] === 3){
            three = true;
            used = key;
            result = 3*key;
        }
    }
    for(var key2 in dict){
        if(dict[key2] === 2 && key2!==used){
            two = true;
            result += 2*key2;
        }
    }
    if(two && three){
        return result;
    }
    else{
        return 0;
    }
}