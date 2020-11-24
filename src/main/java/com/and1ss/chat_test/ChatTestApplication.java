package com.and1ss.chat_test;

import com.and1ss.chat_test.api.dto.*;
import com.and1ss.chat_test.services.AuthTestService;
import com.and1ss.chat_test.services.GroupChatTestService;
import com.and1ss.chat_test.services.rest.impl.GroupChatTestServiceImpl;
import com.and1ss.chat_test.services.PrivateChatTestService;
import com.and1ss.chat_test.services.rest.impl.PrivateChatTestServiceImpl;
import com.and1ss.chat_test.services.rest.impl.AuthTestServiceImpl;
import com.and1ss.group_chat_service.GrpcGroupChatCreationDTO;
import com.and1ss.private_chat_service.*;
import com.and1ss.user_service.GrpcLoginCredentialsDTO;
import com.and1ss.user_service.GrpcRegisterInfoDTO;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class ChatTestApplication {
    private static UUID myUUID = UUID.fromString("a62ddbda-9ffe-492f-bfe7-7c4c33446d4f");
    private static UUID andrewUUID = UUID.fromString("a1dbbefb-6697-42eb-aa75-fca6ba8ffb67");
    private static UUID danyloUUID = UUID.fromString("a1f8f414-bc28-4dca-b6d6-2276b23c8b2f");

    private static final String alexlogin = "alexmaltsevloginf1a433utsdfggahgsjget";
    private static final String andrewlogin = "andrewmolnarlogin14futasdfgjfsghagjgte33";
    private static final String danylologin = "danylodubikasdflogin14fgsutdasdgghtefgj33";
    private static String myAccessToken;

    public static void main(String[] args) {
//        try {
//            runAuthenticationTest();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println("----------------------------GROUP CHAT TEST----------------------");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            runGroupChatsTest();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println("----------------------------PRIVATE CHAT TEST----------------------");
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            runPrivateChatsTest();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

//        runRestAuthenticationTest();
//        runRestGroupChatsTest();
//        runRestPrivateChatsTest();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("----------------------------AUTH TEST----------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        runGrpcAuthenticationTest();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("----------------------------PRIVATE CHAT TEST----------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        runGrpcPrivateChatsTest();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("----------------------------GROUP CHAT TEST----------------------");
        System.out.println();
        System.out.println();
        System.out.println();
        runGrpcGroupChatsTest();

        System.out.println();
        System.out.println();
        System.out.println("---------------------ALL TESTS PASSED-------------------");
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
        System.out.println(Arrays.toString(myGroupChatsArray));

        assert myGroupChatsArray != null;
        UUID groupChatId = myGroupChatsArray[0].getId();


        // Sending messages to group chat
        GroupMessageRetrievalDTO firstGroupMessage = sendRestGroupMessageTo(
                groupChatTestService,
                "This is my first message to group chat! Hi there!",
                groupChatId,
                myAccessToken
        );
        System.out.println(firstGroupMessage);

        GroupMessageRetrievalDTO secondGroupMessage = sendRestGroupMessageTo(
                groupChatTestService,
                "This is my second message to group chat!",
                groupChatId,
                myAccessToken
        );
        System.out.println(secondGroupMessage);

        // Getting all messages for group chat
        GroupMessageRetrievalDTO[] groupMessages = groupChatTestService
                .getAllMessagesForChat(
                        groupChatId,
                        myAccessToken
                ).block();
        System.out.println(Arrays.toString(groupMessages));
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


        // Getting my group chats as array
        final var myGroupChats = groupChatTestService.getAllGroupChats(
                com.and1ss.group_chat_service.GrpcAccessTokenIncomingDTO
                        .newBuilder()
                        .setToken(myAccessToken)
                        .build()
        );

        myGroupChats.getChatsList()
                .forEach(System.out::println);

        assert myGroupChats != null;
        String groupChatId = myGroupChats.getChatsList().get(0).getId();
        System.out.println(groupChatId);

        groupChatTestService.shutdown();
        System.out.println("********************* GROUP CHATS TEST PASSED ******************");
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
        } catch (Exception e) {
            System.out.println("alex is already registered");
        }

        try {
            AccountInfoRetrievalDTO andrewAccountInfo = authTestService.register(andrew)
                    .block();
            assert andrewAccountInfo != null;
            andrewUUID = andrewAccountInfo.getId();
        } catch (Exception e) {
            System.out.println("andrew is already registered");
        }

        try {
            AccountInfoRetrievalDTO danyloAccountInfo = authTestService.register(danylo)
                    .block();
            assert danyloAccountInfo != null;
            danyloUUID = danyloAccountInfo.getId();
        } catch (Exception e) {
            System.out.println("danylo is already registered");
        }

        // Getting my access token
        myAccessToken = authTestService.login(
                new LoginInfoDTO(
                        alexlogin,
                        "alexmaltsevpassword")
        ).block().getAccessToken().toString();
        System.out.println(myAccessToken);
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
        } catch (Exception e) {
            System.out.println("Alex-Andrew private chat already exists");
        }

        PrivateChatCreationDTO alexDanyloPrivateChatCreationDTO =
                new PrivateChatCreationDTO(danyloUUID);

        try {
            PrivateChatRetrievalDTO alexDanyloPrivateChat = privateChatTestService.createPrivateChat(
                    alexDanyloPrivateChatCreationDTO,
                    myAccessToken
            ).block();
        } catch (Exception e) {
            System.out.println("Alex-Danylo private chat already exists");
        }


        // Getting my private chats as array
        PrivateChatRetrievalDTO[] myPrivateChatsArray =
                privateChatTestService.getAllPrivateChats(
                        myAccessToken
                ).block();
        System.out.println(Arrays.toString(myPrivateChatsArray));


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

        System.out.println(alexAndrewPrivateChatId.get());

        // Sending messages to Andrew
        PrivateMessageRetrievalDTO firstMessageToAndrew = sendRestPrivateMessageTo(
                privateChatTestService,
                "This is my first message to Andrew! Hi there!",
                alexAndrewPrivateChatId.get(),
                myAccessToken
        );
        System.out.println(firstMessageToAndrew);

        PrivateMessageRetrievalDTO secondMessageToAndrew = sendRestPrivateMessageTo(
                privateChatTestService,
                "This is my second message to Andrew!",
                alexAndrewPrivateChatId.get(),
                myAccessToken
        );
        System.out.println(secondMessageToAndrew);

        // Getting all private messages with Andrew
        PrivateMessageRetrievalDTO[] privateMessagesWithAndrew = privateChatTestService
                .getAllMessagesForChat(
                        alexAndrewPrivateChatId.get(),
                        myAccessToken
                ).block();
        System.out.println(Arrays.toString(privateMessagesWithAndrew));
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
            System.out.println("alex is already registered");
        }

        try {
            final var andrewAccountInfo = authTestService.register(andrew);
            assert andrewAccountInfo != null;
            andrewUUID = UUID.fromString(andrewAccountInfo.getId());
        } catch (Exception e) {
            System.out.println("andrew is already registered");
        }

        try {
            final var danyloAccountInfo = authTestService.register(danylo);
            assert danyloAccountInfo != null;
            danyloUUID = UUID.fromString(danyloAccountInfo.getId());
        } catch (Exception e) {
            System.out.println("danylo is already registered");
        }

        // Getting my access token
        myAccessToken = authTestService.login(
                GrpcLoginCredentialsDTO.newBuilder()
                        .setLogin(alexlogin)
                        .setPassword("alexmaltsevpassword")
                        .build()
        ).getToken();
        System.out.println(myAccessToken);

        authTestService.shutdown();
        System.out.println("********************* AUTH TEST PASSED ******************");
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
        } catch (Exception e) {
            System.out.println("Alex-Andrew private chat already exists");
        }

        GrpcChatCreationDTO alexDanyloPrivateChatCreationDTO =
                GrpcChatCreationDTO.newBuilder()
                        .setToken(myAccessToken)
                        .setUserId(danyloUUID.toString())
                        .build();

        try {
            final var alexDanyloPrivateChat = privateChatTestService.createPrivateChat(
                    alexDanyloPrivateChatCreationDTO);
        } catch (Exception e) {
            System.out.println("Alex-Danylo private chat already exists");
        }


        // Getting my private chats as array
        GrpcPrivateChatsDTO myPrivateChats =
                privateChatTestService.getAllPrivateChats(
                        GrpcAccessTokenIncomingDTO.newBuilder()
                                .setToken(myAccessToken)
                                .build()
                );
        System.out.println(myPrivateChats.toString());

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

        System.out.println(alexAndrewPrivateChatId.get());

        privateChatTestService.shutdown();
        System.out.println("********************* PRIVATE CHATS TEST PASSED ******************");
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
}
