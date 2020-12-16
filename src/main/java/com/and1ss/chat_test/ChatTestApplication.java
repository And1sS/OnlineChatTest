package com.and1ss.chat_test;

import com.and1ss.chat_test.dto.mqtt.MyMqttMessage;
import com.and1ss.chat_test.dto.rest.*;
import com.and1ss.chat_test.services.rest.AuthTestService;
import com.and1ss.chat_test.services.rest.GroupChatTestService;
import com.and1ss.chat_test.services.rest.impl.GroupChatTestServiceImpl;
import com.and1ss.chat_test.services.rest.PrivateChatTestService;
import com.and1ss.chat_test.services.rest.impl.PrivateChatTestServiceImpl;
import com.and1ss.chat_test.services.rest.impl.AuthTestServiceImpl;
import com.and1ss.group_chat_service.GrpcGroupChatCreationDTO;
import com.and1ss.group_chat_service.GrpcGroupMessageCreationDTO;
import com.and1ss.private_chat_service.*;
import com.and1ss.user_service.GrpcLoginCredentialsDTO;
import com.and1ss.user_service.GrpcRegisterInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class ChatTestApplication {

    private static UUID myUUID = null;
    private static UUID andrewUUID = null;
    private static UUID danyloUUID = null;

    private static final String alexlogin = "alexmaltsevlogin" + new Random().nextInt();
    private static final String andrewlogin = "andrewmolnarlogin" + new Random().nextInt();
    private static final String danylologin = "danylodubiklogin" + new Random().nextInt();

    private static String myAccessToken;

    private static String log;

    public static void main(String[] args) throws MqttException, IOException {
        println();println();println();println();println();println();println();println();println();
        println("############################### TESTS START #################################");

        runRestTests();
        runGrpcTests();
        runMqttTests();

        println("---------------------ALL TESTS PASSED-------------------");
        System.out.println(log);
    }

    private static void runMqttTests() throws MqttException, JsonProcessingException {
        runMqttGroupChatTest();
        runMqttPrivateChatTest();
    }

    private static void runMqttGroupChatTest() throws MqttException, JsonProcessingException {
        com.and1ss.chat_test.services.mqtt.GroupChatTestService mqttGroupChatTestService =
                new com.and1ss.chat_test.services.mqtt.impl.GroupChatTestServiceImpl();
        GroupChatTestService restGroupChatTestService = new GroupChatTestServiceImpl();

        final var myGroupChats = restGroupChatTestService.getAllGroupChats(myAccessToken).block();
        assert myGroupChats.length != 0;

        final var testChat = myGroupChats[0];

        final var groupMessageCreationDTO = GroupMessageCreationDTO.builder()
                .contents("HELLO FROM MQTT!")
                .chatId(testChat.getId())
                .build();

        final var mqttMessageCreationDto = new MyMqttMessage<>(myAccessToken, groupMessageCreationDTO);
        mqttGroupChatTestService.createMessage(mqttMessageCreationDto);

        final List participants = List.of(andrewUUID, danyloUUID);
        final var groupChatCreationDTO = GroupChatCreationDTO.builder()
                .title("Chat created from mqtt!")
                .creator(myUUID)
                .participants(participants)
                .build();

        final var mqttChatCreationDto = new MyMqttMessage<>(myAccessToken, groupChatCreationDTO);
        mqttGroupChatTestService.createGroupChat(mqttChatCreationDto);
        mqttGroupChatTestService.disconnect();
    }

    private static void runMqttPrivateChatTest() throws MqttException, JsonProcessingException {
        com.and1ss.chat_test.services.mqtt.PrivateChatTestService mqttPrivateChatService =
                new com.and1ss.chat_test.services.mqtt.impl.PrivateChatTestServiceImpl();

        AuthTestService authTestService = new AuthTestServiceImpl();
        final var newUser = RegisterInfoDTO.builder()
                .login("user" + new Random().nextInt())
                .password("userpassword")
                .name("user")
                .surname("user")
                .build();
        final var registeredUser = authTestService.register(newUser).block();

        PrivateChatTestService restPrivateChatTestService = new PrivateChatTestServiceImpl();

        final var myGroupChats = restPrivateChatTestService.getAllPrivateChats(myAccessToken).block();
        assert myGroupChats.length != 0;

        final var testChat = myGroupChats[0];

        final var privateMessageCreationDto = new PrivateMessageCreationDTO(testChat.getId(), "HELLO FROM MQTT!");

        final var mqttMessageCreationDto = new MyMqttMessage<>(myAccessToken, privateMessageCreationDto);
        mqttPrivateChatService.createMessage(mqttMessageCreationDto);

        var privateChatCreationDTO = new PrivateChatCreationDTO(registeredUser.getId());
        var mqttChatCreationDto = new MyMqttMessage<>(myAccessToken, privateChatCreationDTO);
        mqttPrivateChatService.createPrivateChat(mqttChatCreationDto);

        mqttPrivateChatService.disconnect();
    }


    private static void runGrpcTests() {
        println("----------------------------GRPC AUTH TEST----------------------");
        runGrpcAuthenticationTest();

        println("----------------------------GRPC PRIVATE CHAT TEST----------------------");
        runGrpcPrivateChatsTest();

        println("----------------------------GRPC GROUP CHAT TEST----------------------");
        runGrpcGroupChatsTest();
    }

    private static void runRestTests() {
        try {
            println("----------------------------REST AUTH TEST----------------------");

            runRestAuthenticationTest();
        } catch (Exception e) {
            println(e.getMessage());
        }

        try {
            println("----------------------------REST GROUP CHAT TEST----------------------");
            runRestGroupChatsTest();
        } catch (Exception e) {
            println(e.getMessage());
        }

        try {
            println("----------------------------REST REST PRIVATE CHAT TEST----------------------");
            runRestPrivateChatsTest();
        } catch (Exception e) {
            println(e.getMessage());
        }
    }

    private static void runRestGroupChatsTest() {
        final GroupChatTestService groupChatTestService =
                new GroupChatTestServiceImpl();

        // Creating my group chat
        GroupChatCreationDTO groupChatCreationDTO =
                GroupChatCreationDTO.builder()
                        .title("This is first group chat!")
                        .about("description")
                        .creator(myUUID)
                        .participants(Arrays.asList(myUUID, andrewUUID, danyloUUID))
                        .build();


        GroupChatRetrievalDTO groupChat = groupChatTestService.createGroupChat(
                groupChatCreationDTO,
                myAccessToken
        ).block();


        // Getting my group chats as array
        GroupChatRetrievalDTO[] myGroupChatsArray =
                groupChatTestService.getAllGroupChats(
                        myAccessToken
                ).block();
        println(Arrays.toString(myGroupChatsArray));

        assert myGroupChatsArray != null;
        UUID groupChatId = myGroupChatsArray[0].getId();


        // Sending messages to group chat
        GroupMessageRetrievalDTO firstGroupMessage = sendRestGroupMessageTo(
                groupChatTestService,
                "This is my first message to group chat! Hi there!",
                groupChatId,
                myAccessToken
        );
        println(firstGroupMessage);

        GroupMessageRetrievalDTO secondGroupMessage = sendRestGroupMessageTo(
                groupChatTestService,
                "This is my second message to group chat!",
                groupChatId,
                myAccessToken
        );
        println(secondGroupMessage);

        // Getting all messages for group chat
        GroupMessageRetrievalDTO[] groupMessages = groupChatTestService
                .getAllMessagesForChat(
                        groupChatId,
                        myAccessToken
                ).block();
        println(Arrays.toString(groupMessages));
    }

    private static void runGrpcGroupChatsTest() {
        final com.and1ss.chat_test.services.grpc.GroupChatTestService groupChatTestService =
                new com.and1ss.chat_test.services.grpc.impl.GroupChatTestServiceImpl();

        // Creating my group chat
        GrpcGroupChatCreationDTO groupChatCreationDTO =
                GrpcGroupChatCreationDTO.newBuilder()
                        .setTitle("This is first group chat!")
                        .setAbout("description")
                        .setToken(myAccessToken)
                        .addAllParticipants(
                                Arrays.asList(
                                        myUUID.toString(),
                                        andrewUUID.toString(),
                                        danyloUUID.toString()
                                )
                        ).build();


        final var groupChat = groupChatTestService.createGroupChat(groupChatCreationDTO);
        println("Created group chat:");
        println(groupChat);

        // Getting my group chats as array
        final var myGroupChats = groupChatTestService.getAllGroupChats(
                com.and1ss.group_chat_service.GrpcAccessTokenIncomingDTO
                        .newBuilder()
                        .setToken(myAccessToken)
                        .build()
        );

        println("Recieved group chats:");
        myGroupChats.getChatsList()
                .forEach(ChatTestApplication::println);

        assert myGroupChats != null;
        String groupChatId = myGroupChats.getChatsList().get(0).getId();
        println(groupChatId);

        groupChatTestService.shutdown();
        println("********************* GROUP CHATS TEST PASSED ******************");
    }

    private static void runRestAuthenticationTest() {
        final AuthTestService authTestService =
                new AuthTestServiceImpl();

        RegisterInfoDTO alex = RegisterInfoDTO.builder()
                .login(alexlogin)
                .password("alexmaltsevpassword")
                .name("Alex")
                .surname("Maltsev")
                .build();

        RegisterInfoDTO andrew = RegisterInfoDTO.builder()
                .login(andrewlogin)
                .password("andrewmolnarpassword")
                .name("Andrew")
                .surname("Molnar")
                .build();

        RegisterInfoDTO danylo = RegisterInfoDTO.builder()
                .login(danylologin)
                .password("danylodubikpassword")
                .name("Danylo")
                .surname("Dubik")
                .build();

        // Registering users
        try {
            AccountInfoRetrievalDTO alexAccountInfo = authTestService.register(alex)
                    .block();
            assert alexAccountInfo != null;
            myUUID = alexAccountInfo.getId();

            println("Registered alex with id: " + myUUID);
        } catch (Exception e) {
            println("alex is already registered");
        }

        try {
            AccountInfoRetrievalDTO andrewAccountInfo = authTestService.register(andrew)
                    .block();
            assert andrewAccountInfo != null;
            andrewUUID = andrewAccountInfo.getId();

            println("Registered andrew with id: " + myUUID);
        } catch (Exception e) {
            println("andrew is already registered");
        }

        try {
            AccountInfoRetrievalDTO danyloAccountInfo = authTestService.register(danylo)
                    .block();
            assert danyloAccountInfo != null;
            danyloUUID = danyloAccountInfo.getId();

            println("Registered danylo with id: " + myUUID);
        } catch (Exception e) {
            println("danylo is already registered");
        }

        println("Got my access token: ");
        // Getting my access token
        myAccessToken = authTestService.login(
                new LoginInfoDTO(
                        alexlogin,
                        "alexmaltsevpassword")
        ).block().getAccessToken().toString();
        println(myAccessToken);
    }

    private static void runRestPrivateChatsTest() {
        final PrivateChatTestService privateChatTestService =
                new PrivateChatTestServiceImpl();

        // Creating my private chats
        PrivateChatCreationDTO alexAndrewPrivateChatCreationDTO =
                new PrivateChatCreationDTO(andrewUUID);

        try {
            PrivateChatRetrievalDTO alexAndrewPrivateChat = privateChatTestService.createPrivateChat(
                    alexAndrewPrivateChatCreationDTO,
                    myAccessToken
            ).block();

            println("Created alex-andrew private chat: ");
            println(alexAndrewPrivateChat);
        } catch (Exception e) {
            println("Alex-Andrew private chat already exists");
        }

        PrivateChatCreationDTO alexDanyloPrivateChatCreationDTO =
                new PrivateChatCreationDTO(danyloUUID);

        try {
            PrivateChatRetrievalDTO alexDanyloPrivateChat = privateChatTestService.createPrivateChat(
                    alexDanyloPrivateChatCreationDTO,
                    myAccessToken
            ).block();

            println("Created alex-danylo private chat: ");
            println(alexDanyloPrivateChat);
        } catch (Exception e) {
            println("Alex-Danylo private chat already exists");
        }


        println("Got my private chats: ");
        // Getting my private chats as array
        PrivateChatRetrievalDTO[] myPrivateChatsArray =
                privateChatTestService.getAllPrivateChats(
                        myAccessToken
                ).block();
        println(Arrays.toString(myPrivateChatsArray));


        // Getting my chat with Andrew Id
        AtomicReference<UUID> alexAndrewPrivateChatId = new AtomicReference<>();

        assert myPrivateChatsArray != null;
        Arrays.stream(myPrivateChatsArray)
                .filter(privateChatRetrievalDTO ->
                        privateChatRetrievalDTO.getUser1Id().equals(myUUID) &&
                                privateChatRetrievalDTO.getUser2Id().equals(andrewUUID)
                ).findAny()
                .ifPresent(alexAndrewPrivateChat -> {
                    alexAndrewPrivateChatId.set(alexAndrewPrivateChat.getId());
                });

        println("Found created alex-andrew private chat:");
        println(alexAndrewPrivateChatId.get());

        // Sending messages to Andrew
        PrivateMessageRetrievalDTO firstMessageToAndrew = sendRestPrivateMessageTo(
                privateChatTestService,
                "This is my first message to Andrew! Hi there!",
                alexAndrewPrivateChatId.get(),
                myAccessToken
        );
        println(firstMessageToAndrew);

        PrivateMessageRetrievalDTO secondMessageToAndrew = sendRestPrivateMessageTo(
                privateChatTestService,
                "This is my second message to Andrew!",
                alexAndrewPrivateChatId.get(),
                myAccessToken
        );
        println(secondMessageToAndrew);

        // Getting all private messages with Andrew
        PrivateMessageRetrievalDTO[] privateMessagesWithAndrew = privateChatTestService
                .getAllMessagesForChat(
                        alexAndrewPrivateChatId.get(),
                        myAccessToken
                ).block();
        println(Arrays.toString(privateMessagesWithAndrew));
    }

    private static void runGrpcAuthenticationTest() {
        final com.and1ss.chat_test.services.grpc.AuthTestService authTestService =
                new com.and1ss.chat_test.services.grpc.impl.AuthTestServiceImpl();

        GrpcRegisterInfoDTO alex = GrpcRegisterInfoDTO.newBuilder()
                .setLogin(alexlogin)
                .setPassword("alexmaltsevpassword")
                .setName("Alex")
                .setSurname("Maltsev")
                .build();

        GrpcRegisterInfoDTO andrew = GrpcRegisterInfoDTO.newBuilder()
                .setLogin(andrewlogin)
                .setPassword("andrewpassword")
                .setName("Andrew")
                .setSurname("Molnar")
                .build();

        GrpcRegisterInfoDTO danylo = GrpcRegisterInfoDTO.newBuilder()
                .setLogin(danylologin)
                .setPassword("danylopassword")
                .setName("Danylo")
                .setSurname("Dubik")
                .build();

        // Registering users
        try {
            final var alexAccountInfo = authTestService.register(alex);
            assert alexAccountInfo != null;
            myUUID = UUID.fromString(alexAccountInfo.getId());
        } catch (Exception e) {
            println("alex is already registered");
        }

        try {
            final var andrewAccountInfo = authTestService.register(andrew);
            assert andrewAccountInfo != null;
            andrewUUID = UUID.fromString(andrewAccountInfo.getId());
        } catch (Exception e) {
            println("andrew is already registered");
        }

        try {
            final var danyloAccountInfo = authTestService.register(danylo);
            assert danyloAccountInfo != null;
            danyloUUID = UUID.fromString(danyloAccountInfo.getId());
        } catch (Exception e) {
            println("danylo is already registered");
        }

        // Getting my access token
        myAccessToken = authTestService.login(
                GrpcLoginCredentialsDTO.newBuilder()
                        .setLogin(alexlogin)
                        .setPassword("alexmaltsevpassword")
                        .build()
        ).getToken();
        println(myAccessToken);

        authTestService.shutdown();
        println("********************* AUTH TEST PASSED ******************");
    }

    private static void runGrpcPrivateChatsTest() {
        final com.and1ss.chat_test.services.grpc.PrivateChatTestService privateChatTestService =
                new com.and1ss.chat_test.services.grpc.impl.PrivateChatTestServiceImpl();

        // Creating my private chats
        GrpcChatCreationDTO alexAndrewPrivateChatCreationDTO =
                GrpcChatCreationDTO.newBuilder()
                        .setToken(myAccessToken)
                        .setUserId(andrewUUID.toString())
                        .build();

        try {
            final var alexAndrewPrivateChat = privateChatTestService.createPrivateChat(
                    alexAndrewPrivateChatCreationDTO);

            println(alexAndrewPrivateChat);
        } catch (Exception e) {
            println("Alex-Andrew private chat already exists");
        }

        GrpcChatCreationDTO alexDanyloPrivateChatCreationDTO =
                GrpcChatCreationDTO.newBuilder()
                        .setToken(myAccessToken)
                        .setUserId(danyloUUID.toString())
                        .build();

        try {
            final var alexDanyloPrivateChat = privateChatTestService.createPrivateChat(
                    alexDanyloPrivateChatCreationDTO);
            println(alexDanyloPrivateChat);
        } catch (Exception e) {
            println("Alex-Danylo private chat already exists");
        }


        // Getting my private chats as array
        GrpcPrivateChatsDTO myPrivateChats =
                privateChatTestService.getAllPrivateChats(
                        GrpcAccessTokenIncomingDTO.newBuilder()
                                .setToken(myAccessToken)
                                .build()
                );
        println(myPrivateChats.toString());

        // Getting my chat with Andrew Id
        AtomicReference<String> alexAndrewPrivateChatId = new AtomicReference<>();

        assert myPrivateChats != null;
        myPrivateChats.getChatsList().stream()
                .filter(privateChatRetrievalDTO ->
                        privateChatRetrievalDTO.getUser1Id().equals(myUUID.toString()) &&
                                privateChatRetrievalDTO.getUser2Id().equals(andrewUUID.toString())
                ).findAny()
                .ifPresent(alexAndrewPrivateChat -> {
                    alexAndrewPrivateChatId.set(alexAndrewPrivateChat.getId());
                });

        println("Found alex-andrew private chat:");
        println(alexAndrewPrivateChatId.get());

        GrpcPrivateMessageCreationDTO grpcPrivateMessageCreationDTO = GrpcPrivateMessageCreationDTO.newBuilder()
                .setToken(myAccessToken)
                .setChatId(alexAndrewPrivateChatId.get())
                .setContents("This is my message to alex-andrew private chat!")
                .build();

        var createdMessage = privateChatTestService.createMessage(grpcPrivateMessageCreationDTO);
        println("Created alex-andrew private message:");
        println(createdMessage);


        grpcPrivateMessageCreationDTO = GrpcPrivateMessageCreationDTO.newBuilder()
                .setToken(myAccessToken)
                .setChatId(alexAndrewPrivateChatId.get())
                .setContents("This is my second message to alex-andrew private chat!")
                .build();

        createdMessage = privateChatTestService.createMessage(grpcPrivateMessageCreationDTO);
        println("Created alex-andrew private message:");
        println(createdMessage);

        final var grpcChatRetrievalDTO = GrpcChatRetrievalDTO.newBuilder()
                .setToken(myAccessToken)
                .setChatId(alexAndrewPrivateChatId.get())
                .build();

        final var alexAndrewPrivateMessages = privateChatTestService
                .getAllMessagesForChat(grpcChatRetrievalDTO);

        println("Got messages for alex-andrew private chat:");
        alexAndrewPrivateMessages.getMessagesList().forEach(ChatTestApplication::println);
        privateChatTestService.shutdown();
        println("********************* PRIVATE CHATS TEST PASSED ******************");
    }

    private static PrivateMessageRetrievalDTO
    sendRestPrivateMessageTo(
            PrivateChatTestService privateChatTestService,
            String message,
            UUID privateChatId,
            String accessToken
    ) {
        PrivateMessageCreationDTO privateMessageCreationDTO =
                new PrivateMessageCreationDTO(message);
        return privateChatTestService
                .createMessage(
                        privateMessageCreationDTO,
                        privateChatId,
                        accessToken
                ).block();
    }


    private static GroupMessageRetrievalDTO
    sendRestGroupMessageTo(
            GroupChatTestService groupChatTestService,
            String message,
            UUID groupChatId,
            String accessToken
    ) {
        GroupMessageCreationDTO groupMessageCreationDTO =
                new GroupMessageCreationDTO(message);
        return groupChatTestService
                .createMessage(
                        groupMessageCreationDTO,
                        groupChatId,
                        accessToken
                ).block();
    }

    private static void println() {
        log += "\n\n\n\n";
    }

    private static void println(String msg) {
        log += msg + "\n\n\n\n";
    }

    private static void println(Object o) {
        log += o.toString() + "\n\n\n\n";
    }
}
