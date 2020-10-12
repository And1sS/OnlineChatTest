package com.and1ss.chat_test;

import com.and1ss.chat_test.api.dto.*;
import com.and1ss.chat_test.services.AuthTestService;
import com.and1ss.chat_test.services.GroupChatTestService;
import com.and1ss.chat_test.services.impl.GroupChatTestServiceImpl;
import com.and1ss.chat_test.services.PrivateChatTestService;
import com.and1ss.chat_test.services.impl.PrivateChatTestServiceImpl;
import com.and1ss.chat_test.services.impl.AuthTestServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class ChatTestApplication {
    private static UUID myUUID = UUID.fromString("a62ddbda-9ffe-492f-bfe7-7c4c33446d4f");
    private static UUID andrewUUID = UUID.fromString("a1dbbefb-6697-42eb-aa75-fca6ba8ffb67");
    private static UUID danyloUUID = UUID.fromString("a1f8f414-bc28-4dca-b6d6-2276b23c8b2f");

    private static String myAccessToken;

    public static void main(String[] args) {
        runAuthenticationTest();
        runGroupChatsTest();
        runPrivateChatsTest();
    }

    private static void runGroupChatsTest() {
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
        GroupMessageRetrievalDTO firstGroupMessage = sendGroupMessageTo(
                groupChatTestService,
                "This is my first message to group chat! Hi there!",
                groupChatId,
                myAccessToken
        );
        System.out.println(firstGroupMessage);

        GroupMessageRetrievalDTO secondGroupMessage = sendGroupMessageTo(
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

    private static void runAuthenticationTest() {
        final AuthTestService authTestService =
                new AuthTestServiceImpl();

        RegisterInfoDTO alex = RegisterInfoDTO.builder()
                .login("alexmaltsevlogin")
                .password("alexmaltsevpassword")
                .name("Alex")
                .surname("Maltsev")
                .build();

        RegisterInfoDTO andrew = RegisterInfoDTO.builder()
                .login("andrewmolnarlogin")
                .password("andrewmolnarpassword")
                .name("Andrew")
                .surname("Molnar")
                .build();

        RegisterInfoDTO danylo = RegisterInfoDTO.builder()
                .login("danylodubiklogin")
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
                        "alexmaltsevlogin",
                        "alexmaltsevpassword")
        ).block().getAccessToken().toString();
        System.out.println(myAccessToken);
    }

    private static void runPrivateChatsTest() {
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
        PrivateMessageRetrievalDTO firstMessageToAndrew = sendPrivateMessageTo(
                privateChatTestService,
                "This is my first message to Andrew! Hi there!",
                alexAndrewPrivateChatId.get(),
                myAccessToken
        );
        System.out.println(firstMessageToAndrew);

        PrivateMessageRetrievalDTO secondMessageToAndrew = sendPrivateMessageTo(
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

    private static PrivateMessageRetrievalDTO
    sendPrivateMessageTo(
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
    sendGroupMessageTo(
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
