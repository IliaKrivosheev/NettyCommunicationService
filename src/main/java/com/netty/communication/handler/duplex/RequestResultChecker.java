package com.netty.communication.handler.duplex;

import com.netty.communication.exception.ServerNotResponseException;
import com.netty.communication.exception.ServerRuntimeException;
import com.netty.communication.message.ResponseMessage;
import com.netty.communication.specification.response.ResponseSpec;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * Handler that checks the result of a request by monitoring the responses.
 * This handler is responsible for completing a CompletableFuture when a response is received
 * or if a timeout occurs due to the server not responding.
 */
@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
@Slf4j
public class RequestResultChecker extends ChannelDuplexHandler {
    private final CompletableFuture<Void> completableFuture = new CompletableFuture<>();

    /**
     * Processes the received response message.
     * If the response is OK, the CompletableFuture is completed successfully.
     * If the response contains an error, the CompletableFuture is completed exceptionally
     * with a ServerRuntimeException.
     *
     * @param ctx the ChannelHandlerContext for the current channel
     * @param msg the received message
     * @throws Exception if an error occurs while processing the message
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ResponseMessage response) {
            if (response.responseSpec() == ResponseSpec.OK) {
                completableFuture.complete(null);
            } else {
                var spec = response.responseSpec();
                completableFuture.completeExceptionally(new ServerRuntimeException(spec.getErrorNo(), spec.getErrorMessage()));
            }
        }
    }

    /**
     * Handles exceptions thrown during request processing.
     * Completes the CompletableFuture exceptionally with the given cause.
     *
     * @param ctx   the ChannelHandlerContext for the current channel
     * @param cause the throwable that was caught
     * @throws Exception if an error occurs while handling the exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        completableFuture.completeExceptionally(cause);
    }

    /**
     * Handles idle state events triggered by the IdleStateHandler.
     * If the idle state is ALL_IDLE, it completes the CompletableFuture exceptionally
     * with a ServerNotResponseException.
     *
     * @param ctx the ChannelHandlerContext for the current channel
     * @param evt the event triggered
     * @throws Exception if an error occurs while processing the event
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent e) {
            if (e.state() == IdleState.ALL_IDLE) {
                completableFuture.completeExceptionally(new ServerNotResponseException());
            }
        }
    }

    /**
     * Handles the case where the channel becomes inactive.
     * Completes the CompletableFuture exceptionally with a RuntimeException indicating
     * that the channel has been closed.
     *
     * @param ctx the ChannelHandlerContext for the current channel
     * @throws Exception if an error occurs while processing the channel inactivity
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        completableFuture.completeExceptionally(new RuntimeException("The channel was closed."));
        super.channelInactive(ctx);
    }
}
