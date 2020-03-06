/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.duoec.web.cn.core.configure.http;

import org.apache.commons.io.input.TeeInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * description
 *
 * @author chenruifeng
 * @since 2020/2/21 20:50
 */
public class TeeServletInputStream extends ServletInputStream {

    private TeeInputStream targetStream;

    public TeeServletInputStream(InputStream one, OutputStream two) {
        targetStream = new TeeInputStream(one, two);
    }

    @Override
    public int read() throws IOException {
        return targetStream.read();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.targetStream.close();
    }

    /**
     * Has the end of this InputStream been reached?
     *
     * @return <code>true</code> if all the data has been read from the stream,
     * else <code>false</code>
     * @since Servlet 3.1
     */
    @Override
    public boolean isFinished() {
        return true;
    }

    /**
     * Can data be read from this InputStream without blocking?
     * Returns  If this method is called and returns false, the container will
     * invoke {@link ReadListener#onDataAvailable()} when data is available.
     *
     * @return <code>true</code> if data can be read without blocking, else
     * <code>false</code>
     * @since Servlet 3.1
     */
    @Override
    public boolean isReady() {
        return true;
    }

    /**
     * Sets the {@link ReadListener} for this {@link ServletInputStream} and
     * thereby switches to non-blocking IO. It is only valid to switch to
     * non-blocking IO within async processing or HTTP upgrade processing.
     *
     * @param listener The non-blocking IO read listener
     * @throws IllegalStateException If this method is called if neither
     *                               async nor HTTP upgrade is in progress or
     *                               if the {@link ReadListener} has already
     *                               been set
     * @throws NullPointerException  If listener is null
     * @since Servlet 3.1
     */
    @Override
    public void setReadListener(ReadListener listener) {

    }
}
