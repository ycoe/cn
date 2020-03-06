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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * description
 *
 * @author chenruifeng
 * @since 2020/2/9 15:14
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper {

    TeeServletOutputStream targetOutputStream;

    /**
     * write from response.getOutputStream()
     */
    ByteArrayOutputStream bos;

    public BufferedResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public String getResponseData() {
        if (bos != null) {
            return bos.toString();
        }
        throw new RuntimeException("response OutputStream is null");
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (targetOutputStream == null) {
            bos = new ByteArrayOutputStream();
            targetOutputStream = new TeeServletOutputStream(getResponse().getOutputStream(), bos);
        }
        return targetOutputStream;
    }

}
