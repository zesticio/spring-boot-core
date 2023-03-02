/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.zestic.core.entity;

import java.io.UnsupportedEncodingException;
import io.zestic.core.exception.NotEnoughDataInByteBufferException;
import io.zestic.core.exception.TerminatingZeroNotFoundException;
import io.zestic.core.util.Buffer;

public class Message extends Model<String, Message> {

    private Fields fields = new Fields();

    /**
     * transaction id, unique for every transaction
     */
    private String id = "";
    /**
     * Used to associate the current message with a previous message.
     * This header is commonly used to associate a response message with a
     * request message.
     */
    private String correlationId = "";
    private Header header = null;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setHeader(Buffer buffer) throws NotEnoughDataInByteBufferException, TerminatingZeroNotFoundException, UnsupportedEncodingException {
        checkHeader();
        header.setData(buffer);
    }

    protected void checkHeader() {
        if (header == null) {
            header = new Header();
        }
    }

    public void setCommandLength(int length) {
        checkHeader();
        header.setCommandLength(length);
    }

    public int getCommandLength() {
        checkHeader();
        return header.getCommandLength();
    }

    public void setCommandStatus(int status) {
        checkHeader();
        this.header.setCommandStatus(status);
    }

    public int getCommandStatus() {
        checkHeader();
        return header.getCommandStatus();
    }

    public int getCommandId() {
        checkHeader();
        return header.getCommandId();
    }

    public void setCommandType(int type) {
        checkHeader();
        this.header.setCommandType(type);
    }

    public int getCommandType() {
        checkHeader();
        return this.header.getCommandType();
    }

    public String getMessageId() {
        return id;
    }

    public void setMessageId(String value) {
        this.id = value;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String value) {
        this.correlationId = value;
    }


    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        String header = String.format("0x%08X 0x%08X",
                getCommandId(),
                getCommandStatus()
        );
        buffer.append(String.format("%-50s", header));
        buffer.append(String.format("%-11s%-39s", "message-id", getMessageId()));
        return buffer.toString();
    }
}
