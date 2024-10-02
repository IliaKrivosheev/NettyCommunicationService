package com.netty.communication.pipeline;

import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;

import java.util.function.Supplier;

/**
 * Factory for creating instances of {@link ChannelHandler} with optional
 * association to an {@link EventLoopGroup}.
 */
public final class HandlerFactory {

    private final Supplier<ChannelHandler> handlerSupplier;
    private final EventLoopGroup workGroup;

    /**
     * Creates a new {@link HandlerFactory} with the specified handler supplier.
     *
     * @param handlerSupplier a supplier that provides instances of {@link ChannelHandler}
     * @return a new instance of {@link HandlerFactory}
     */
    public static HandlerFactory of(Supplier<ChannelHandler> handlerSupplier) {
        return new HandlerFactory(null, handlerSupplier);
    }

    /**
     * Creates a new {@link HandlerFactory} with the specified event loop group
     * and handler supplier.
     *
     * @param workGroup      the {@link EventLoopGroup} to associate with the factory
     * @param handlerSupplier a supplier that provides instances of {@link ChannelHandler}
     * @return a new instance of {@link HandlerFactory}
     */
    public static HandlerFactory of(EventLoopGroup workGroup, Supplier<ChannelHandler> handlerSupplier) {
        return new HandlerFactory(workGroup, handlerSupplier);
    }

    private HandlerFactory(EventLoopGroup workGroup, Supplier<ChannelHandler> handlerSupplier) {
        this.workGroup = workGroup;
        this.handlerSupplier = handlerSupplier;
    }

    /**
     * Gets the associated {@link EventLoopGroup}.
     *
     * @return the associated {@link EventLoopGroup}, or {@code null} if none
     */
    public EventLoopGroup workGroup() {
        return workGroup;
    }

    /**
     * Gets an instance of {@link ChannelHandler} using the supplier.
     *
     * @return a new instance of {@link ChannelHandler}
     */
    public ChannelHandler handler() {
        return handlerSupplier.get();
    }
}
