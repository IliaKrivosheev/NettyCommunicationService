package com.netty.communication.util;

import io.netty.channel.ChannelFuture;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for propagating the result of a {@link ChannelFuture} to a {@link CompletableFuture}.
 * <p>
 * This class provides methods to handle the completion and error states of Netty's ChannelFuture,
 * allowing the user to manage asynchronous operations more effectively.
 */
public final class PropagateChannelFuture {

    /**
     * Propagates the result of the given {@link ChannelFuture} to the specified {@link CompletableFuture}.
     *
     * @param channelFuture the ChannelFuture whose result is to be propagated
     * @param userFuture    the CompletableFuture to which the result should be propagated
     */
    public static void propagate(ChannelFuture channelFuture, CompletableFuture<Void> userFuture) {
        if (channelFuture.cause() != null) {
            userFuture.completeExceptionally(channelFuture.cause());
            return;
        }

        if (channelFuture.isCancelled()) {
            userFuture.completeExceptionally(new CancellationException("The ChannelFuture was cancelled."));
            return;
        }

        userFuture.complete(null);
    }

    private PropagateChannelFuture() {}
}
