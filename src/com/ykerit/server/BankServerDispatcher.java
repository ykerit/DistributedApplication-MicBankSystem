package com.ykerit.server;

import com.ykerit.message.*;
import org.greatfree.client.OutMessageStream;
import org.greatfree.concurrency.interactive.NotificationDispatcher;
import org.greatfree.concurrency.interactive.RequestDispatcher;
import org.greatfree.data.ServerConfig;
import org.greatfree.message.ServerMessage;
import org.greatfree.server.ServerDispatcher;

import java.util.Calendar;

public class BankServerDispatcher extends ServerDispatcher<ServerMessage> {

    private NotificationDispatcher<DepositNotification, DepositNotificationThread, DepositNotificationThreadCreator> depositNotificationDispatcher;
    private NotificationDispatcher<WithDrawNotification, WithDrawNotificationThread, WithDrawNotificationThreadCreator> withDrawNotificationDispatcher;
    private RequestDispatcher<CheckAccountRequest, CheckAccountStream, CheckAccountResponse, CheckAccountRequestThread, CheckAccountRequestThreadCreator> checkAccountDispatcher;
    private RequestDispatcher<BankLoginRequest, BankLoginStream, BankLoginResponse, BankLoginRequestThread, BankLoginRequestThreadCreator> bankLoginDispatcher;
    private RequestDispatcher<ViewUserListRequest, ViewUserListStream, ViewUserListResponse, ViewUserListRequestThread, ViewUserListRequestThreadCreator> viewUserListRequestDispatcher;
    private NotificationDispatcher<ShutdownBankServerNotification, ShutdownBankServerThread, ShutdownBankServerThreadCreator> shutdownBankServerNotificationDispatcher;

    public BankServerDispatcher(int serverThreadPoolSize, long serverThreadKeepAliveTime, int schedulerPoolSize, long schedulerKeepAliveTime)
    {
        super(serverThreadPoolSize, serverThreadKeepAliveTime, schedulerPoolSize, schedulerKeepAliveTime);

        this.depositNotificationDispatcher = new NotificationDispatcher.NotificationDispatcherBuilder<DepositNotification, DepositNotificationThread, DepositNotificationThreadCreator>()
                .poolSize(ServerConfig.NOTIFICATION_DISPATCHER_POOL_SIZE)
                .threadCreator(new DepositNotificationThreadCreator())
                .notificationQueueSize(ServerConfig.NOTIFICATION_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
        this.withDrawNotificationDispatcher = new NotificationDispatcher.NotificationDispatcherBuilder<WithDrawNotification, WithDrawNotificationThread, WithDrawNotificationThreadCreator>()
                .poolSize(ServerConfig.NOTIFICATION_DISPATCHER_POOL_SIZE)
                .threadCreator(new WithDrawNotificationThreadCreator())
                .notificationQueueSize(ServerConfig.NOTIFICATION_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
        this.shutdownBankServerNotificationDispatcher = new NotificationDispatcher.NotificationDispatcherBuilder<ShutdownBankServerNotification, ShutdownBankServerThread, ShutdownBankServerThreadCreator>()
                .poolSize(ServerConfig.NOTIFICATION_DISPATCHER_POOL_SIZE)
                .threadCreator(new ShutdownBankServerThreadCreator())
                .notificationQueueSize(ServerConfig.NOTIFICATION_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.NOTIFICATION_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.NOTIFICATION_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
        this.checkAccountDispatcher = new RequestDispatcher.RequestDispatcherBuilder<CheckAccountRequest, CheckAccountStream, CheckAccountResponse, CheckAccountRequestThread, CheckAccountRequestThreadCreator>()
                .poolSize(ServerConfig.REQUEST_DISPATCHER_POOL_SIZE)
                .threadCreator(new CheckAccountRequestThreadCreator())
                .requestQueueSize(ServerConfig.REQUEST_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.REQUEST_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.REQUEST_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
        this.bankLoginDispatcher = new RequestDispatcher.RequestDispatcherBuilder<BankLoginRequest, BankLoginStream, BankLoginResponse, BankLoginRequestThread, BankLoginRequestThreadCreator>()
                .poolSize(ServerConfig.REQUEST_DISPATCHER_POOL_SIZE)
                .threadCreator(new BankLoginRequestThreadCreator())
                .requestQueueSize(ServerConfig.REQUEST_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.REQUEST_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.REQUEST_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
        this.viewUserListRequestDispatcher = new RequestDispatcher.RequestDispatcherBuilder<ViewUserListRequest, ViewUserListStream, ViewUserListResponse, ViewUserListRequestThread, ViewUserListRequestThreadCreator>()
                .poolSize(ServerConfig.REQUEST_DISPATCHER_POOL_SIZE)
                .threadCreator(new ViewUserListRequestThreadCreator())
                .requestQueueSize(ServerConfig.REQUEST_QUEUE_SIZE)
                .dispatcherWaitTime(ServerConfig.REQUEST_DISPATCHER_WAIT_TIME)
                .waitRound(ServerConfig.REQUEST_DISPATCHER_WAIT_ROUND)
                .idleCheckDelay(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_DELAY)
                .idleCheckPeriod(ServerConfig.REQUEST_DISPATCHER_IDLE_CHECK_PERIOD)
                .scheduler(super.getScheduler())
                .build();
    }

    @Override
    public void dispose(long timeout) throws InterruptedException
    {
        this.depositNotificationDispatcher.dispose();
        this.withDrawNotificationDispatcher.dispose();
        this.checkAccountDispatcher.dispose();
        this.bankLoginDispatcher.dispose();
        this.shutdownBankServerNotificationDispatcher.dispose();
        this.viewUserListRequestDispatcher.dispose();
        super.shutdown(timeout);
    }

    @Override
    public void process(OutMessageStream<ServerMessage> message) {
        switch (message.getMessage().getType())
        {
            case MessageType.DEPOSIT_NITIFICATION:
                System.out.println("DEPOSIT_NOTIFICATION received @" + Calendar.getInstance().getTime());

                if (!this.depositNotificationDispatcher.isReady())
                {
                    super.execute(this.depositNotificationDispatcher);
                }
                this.depositNotificationDispatcher.enqueue((DepositNotification) message.getMessage());
                break;

            case MessageType.WITHDRAW_NITIFICATION:
                System.out.println("WITHDRAW_NOTIFICATION received @" + Calendar.getInstance().getTime());
                if (!this.withDrawNotificationDispatcher.isReady()) {
                    super.execute(this.withDrawNotificationDispatcher);
                }
                this.withDrawNotificationDispatcher.enqueue((WithDrawNotification) message.getMessage());
                break;
            case MessageType.SHUTDOWN_NITIFICATION:
                System.out.println("SHUTDOWN_NOTIFICATION received @" + Calendar.getInstance().getTime());
                if (!this.shutdownBankServerNotificationDispatcher.isReady()) {
                    super.execute(this.shutdownBankServerNotificationDispatcher);
                }
                this.shutdownBankServerNotificationDispatcher.enqueue((ShutdownBankServerNotification) message.getMessage());
                break;
            case MessageType.CHECKACCOUNT_REQUEST:
                System.out.println("CHECK_ACCOUNT_REQUEST received @" + Calendar.getInstance().getTime());
                if (!this.checkAccountDispatcher.isReady()) {
                    super.execute(this.checkAccountDispatcher);
                }
                this.checkAccountDispatcher.enqueue(new CheckAccountStream(message.getOutStream(), message.getLock(), (CheckAccountRequest) message.getMessage()));
                break;
            case MessageType.BANKLOGIN_REQUEST:
                System.out.println("BANK_LOGIN_REQUEST received @" + Calendar.getInstance().getTime());
                if (!this.bankLoginDispatcher.isReady()) {
                    super.execute(this.bankLoginDispatcher);
                }
                this.bankLoginDispatcher.enqueue(new BankLoginStream(message.getOutStream(), message.getLock(), (BankLoginRequest) message.getMessage()));
                break;
            case MessageType.VIEW_USER_LIST_REQUEST:
                System.out.println("VIEW_USER_LIST_REQUEST received @" + Calendar.getInstance().getTime());
                if (!this.viewUserListRequestDispatcher.isReady()) {
                    super.execute(this.viewUserListRequestDispatcher);
                }
                this.viewUserListRequestDispatcher.enqueue(new ViewUserListStream(message.getOutStream(), message.getLock(), (ViewUserListRequest) message.getMessage()));
                break;
        }
    }
}

