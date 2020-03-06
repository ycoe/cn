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

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * description
 *
 * @author chenruifeng
 * @since 2020/2/9 15:15
 */
public class TeeServletOutputStream extends ServletOutputStream {

    private final TeeOutputStream targetStream;

    public TeeServletOutputStream(OutputStream one, OutputStream two) {
        targetStream = new TeeOutputStream(one, two);
    }

    @Override
    public void write(int arg0) throws IOException {
        this.targetStream.write(arg0);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        this.targetStream.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.targetStream.close();
    }

    /**
     * Checks if a non-blocking write will succeed. If this returns
     * <code>false</code>, it will cause a callback to
     * {@link WriteListener#onWritePossible()} when the buffer has emptied. If
     * this method returns <code>false</code> no further data must be written
     * until the contain calls {@link WriteListener#onWritePossible()}.
     *
     * @return <code>true</code> if data can be written, else <code>false</code>
     * @since Servlet 3.1
     */
    @Override
    public boolean isReady() {
        return true;
    }

    /**
     * Sets the {@link WriteListener} for this {@link ServletOutputStream} and
     * thereby switches to non-blocking IO. It is only valid to switch to
     * non-blocking IO within async processing or HTTP upgrade processing.
     *
     * @param listener The non-blocking IO write listener
     * @throws IllegalStateException If this method is called if neither
     *                               async nor HTTP upgrade is in progress or
     *                               if the {@link WriteListener} has already
     *                               been set
     * @throws NullPointerException  If listener is null
     * @since Servlet 3.1
     */
    @Override
    public void setWriteListener(WriteListener listener) {

    }
}
