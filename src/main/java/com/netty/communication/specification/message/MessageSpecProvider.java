package com.netty.communication.specification.message;

import com.competitive.message.*;
import com.competitive.processor.*;
import com.netty.communication.message.*;
import com.netty.communication.processor.*;
import com.netty.communication.specification.channel.ChannelSpecProvider;
import org.springframework.stereotype.Component;

/**
 * The MessageSpecProvider is responsible for managing protocol IDs, message decoding,
 * and processing inbound and outbound requests. It integrates with various managers
 * to register and retrieve protocol information, decoders, and processors.
 */
@Component
public class MessageSpecProvider implements
        ProtocolIdProvider, MessageDecoderProvider, InboundRequestProcessorProvider, OutboundRequestProcessorProvider {

    private final ChannelSpecProvider channelSpec;
    private final ProtocolIdManager protocolIdManager;
    private final MessageDecoderManager messageDecoderManager;
    private final InboundRequestProcessorManager inboundRequestProcessorManager;
    private final OutboundRequestProcessorManager outboundRequestProcessorManager;

    /**
     * Constructor that initializes the necessary managers and configures them.
     *
     * @param channelSpec the specification provider for channel configurations
     */
    public MessageSpecProvider(ChannelSpecProvider channelSpec) {
        this.channelSpec = channelSpec;
        protocolIdManager = new ProtocolIdManager();
        messageDecoderManager = new MessageDecoderManager();
        inboundRequestProcessorManager = new InboundRequestProcessorManager();
        outboundRequestProcessorManager = new OutboundRequestProcessorManager();
        configureProtocolIdManager();
        configureMessageDecoderManager();
        configureInboundRequestProcessorManager();
        configureOutboundRequestProcessorManager();
    }

    /**
     * Configures the ProtocolIdManager by mapping message classes to protocol IDs.
     */
    private void configureProtocolIdManager() {
        protocolIdManager.put(FileDownloadRequest.class, 1001);
        protocolIdManager.put(FileUploadRequest.class, 1002);
        protocolIdManager.put(InboundFileChunk.class, 2001);
        protocolIdManager.put(OutboundFileChunk.class, 2001);
        protocolIdManager.put(ResponseMessage.class, 3001);
        protocolIdManager.put(ChunkTransferOk.class, 4001);
    }

    /**
     * Configures the MessageDecoderManager by registering decoders for message IDs.
     */
    private void configureMessageDecoderManager() {
        messageDecoderManager.put(1001, FileDownloadRequest::decode);
        messageDecoderManager.put(1002, FileUploadRequest::decode);
        messageDecoderManager.put(2001, InboundFileChunk::decode);
        messageDecoderManager.put(3001, ResponseMessage::decode);
        messageDecoderManager.put(4001, ChunkTransferOk::decode);
    }

    /**
     * Configures the InboundRequestProcessorManager by mapping message classes to their inbound processors.
     */
    private void configureInboundRequestProcessorManager() {
        inboundRequestProcessorManager.put(
                FileDownloadRequest.class,
                FileDownloadInboundRequestProcessor.builder()
                        .chunkSize(channelSpec.server().chunkSize())
                        .rootPath(channelSpec.server().rootPath())
                        .fileTransferProcessor(new CommonFileChunkTransferProcessor())
                        .build());

        inboundRequestProcessorManager.put(
                FileUploadRequest.class,
                EmptyBodyRetrieveProcessor.INSTANCE);
    }

    /**
     * Configures the OutboundRequestProcessorManager by mapping message classes to their outbound processors.
     */
    private void configureOutboundRequestProcessorManager() {
        outboundRequestProcessorManager.put(
                UserFileDownloadRequest.class,
                FileDownloadOutboundRequestProcessor.builder()
                        .build());

        outboundRequestProcessorManager.put(
                UserFileUploadRequest.class,
                FileUploadOutboundRequestProcessor.builder()
                        .chunkSize(channelSpec.client().chunkSize())
                        .rootPath(channelSpec.client().rootPath())
                        .fileTransferProcessor(new CommonFileChunkTransferProcessor())
                        .build());
    }

    /**
     * Retrieves the protocol ID associated with a specific message class.
     *
     * @param clazz the class of the message
     * @return the protocol ID
     */
    @Override
    public int getProtocolId(Class<? extends MessageEncodable> clazz) {
        return protocolIdManager.get(clazz);
    }

    /**
     * Retrieves the decoder function associated with a specific message ID.
     *
     * @param id the ID of the message
     * @return the decode function for the message
     */
    @Override
    public DecodeFunction getDecoder(int id) {
        return messageDecoderManager.get(id);
    }

    /**
     * Retrieves the outbound request processor associated with a specific user request class.
     *
     * @param clazz the class of the user request
     * @return the outbound request processor
     */
    @Override
    public OutboundRequestProcessor getOutboundRequestProcessor(Class<? extends UserRequest> clazz) {
        return outboundRequestProcessorManager.get(clazz);
    }

    /**
     * Retrieves the inbound request processor associated with a specific protocol message class.
     *
     * @param clazz the class of the protocol message
     * @return the inbound request processor
     */
    @Override
    public InboundRequestProcessor getInboundRequestProcessor(Class<? extends ProtocolMessage> clazz) {
        return inboundRequestProcessorManager.get(clazz);
    }
}