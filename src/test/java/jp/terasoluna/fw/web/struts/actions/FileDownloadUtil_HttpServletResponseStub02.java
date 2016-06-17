/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.struts.actions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.DelegatingServletOutputStream;

/**
 * FileDownloadUtilTest�ŗ��p����B
 * IOException�𔭐������邽�߂̃N���X�B
 *
 */
public class FileDownloadUtil_HttpServletResponseStub02 implements
        HttpServletResponse {

    private final ByteArrayOutputStream content = new ByteArrayOutputStream();

    private PrintWriter writer;

    @SuppressWarnings("unused")
    private boolean committed;

    private final DelegatingServletOutputStream outputStream = new DelegatingServletOutputStream(
            this.content);

    public ServletOutputStream getOutputStream() throws IOException {
        throw new IOException();
    }

    public void flushBuffer() {
        if (this.writer != null) {
            this.writer.flush();
        }
        if (this.outputStream != null) {
            try {
                this.outputStream.flush();
            } catch (IOException ex) {
                throw new IllegalStateException(
                        "Could not flush OutputStream: " + ex.getMessage());
            }
        }
        this.committed = true;
    }

    public byte[] getContentAsByteArray() {
        flushBuffer();
        return this.content.toByteArray();
    }

    public void addCookie(Cookie arg0) {

    }

    public void addDateHeader(String arg0, long arg1) {

    }

    public void addHeader(String arg0, String arg1) {

    }

    public void addIntHeader(String arg0, int arg1) {

    }

    public boolean containsHeader(String arg0) {

        return false;
    }

    public String encodeRedirectURL(String arg0) {

        return null;
    }

    @SuppressWarnings("deprecation")
    public String encodeRedirectUrl(String arg0) {

        return null;
    }

    public String encodeURL(String arg0) {

        return null;
    }

    @SuppressWarnings("deprecation")
    public String encodeUrl(String arg0) {

        return null;
    }

    public void sendError(int arg0) throws IOException {

    }

    public void sendError(int arg0, String arg1) throws IOException {

    }

    public void sendRedirect(String arg0) throws IOException {

    }

    public void setDateHeader(String arg0, long arg1) {

    }

    public void setHeader(String arg0, String arg1) {

    }

    public void setIntHeader(String arg0, int arg1) {

    }

    public void setStatus(int arg0) {

    }

    @SuppressWarnings("deprecation")
    public void setStatus(int arg0, String arg1) {

    }

    public int getBufferSize() {

        return 0;
    }

    public String getCharacterEncoding() {

        return null;
    }

    public String getContentType() {

        return null;
    }

    public Locale getLocale() {

        return null;
    }

    public PrintWriter getWriter() throws IOException {

        return null;
    }

    public boolean isCommitted() {

        return false;
    }

    public void reset() {

    }

    public void resetBuffer() {

    }

    public void setBufferSize(int arg0) {

    }

    public void setCharacterEncoding(String arg0) {

    }

    public void setContentLength(int arg0) {

    }

    public void setContentType(String arg0) {

    }

    public void setLocale(Locale arg0) {

    }

}
