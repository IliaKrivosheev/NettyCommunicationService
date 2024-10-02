# Competitive Networking Service

## Overview

The Competitive Networking Service is a standalone service designed to facilitate high-performance TCP communication using the Netty framework. This project provides a robust architecture for handling TCP client and server operations, allowing for efficient data transmission and management.

## Features

- **TCP Client and Server**: Implements reliable TCP client and server functionalities, allowing for real-time communication.
- **Handler Management**: Utilizes handler factories for dynamic handler management in the server pipeline.
- **Read/Write Operations**: Supports synchronous and asynchronous read/write operations, ensuring efficient data handling.
- **File Utilities**: Includes utilities for file manipulation, such as reading from and writing to files with ByteBuf.
- **Performance Monitoring**: Utilizes `StopWatch` for tracking operation durations and performance metrics.
- **Exception Handling**: Comprehensive error handling mechanisms for both network and file operations.

## Components

### 1. TCP Client
- Implements `TcpClient` interface for connecting, disconnecting, and sending/receiving data.
- Supports reading data synchronously or asynchronously.

### 2. TCP Server
- Implements `TcpServer` interface for handling client connections and data transmission.
- Manages active client channels and their states.

### 3. Utilities
- **File Utilities**: Provides methods for file reading/writing and file size calculations.
- **Future Utilities**: Handles `CompletableFuture` operations for network tasks.
- **Channel Utilities**: Accesses Netty channel attributes and pipelines.

## Usage

To run the Competitive Networking Service, ensure you have Java 17 and the required dependencies in your build file.