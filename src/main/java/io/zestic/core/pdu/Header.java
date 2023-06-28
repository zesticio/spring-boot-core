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

package io.zestic.core.pdu;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class Header implements Serializable {

    /**
     * The command length field defines the total octet length of the PDU packet including the length field
     */
    private Integer commandLength = 0x00;
    /**
     * The command id field identifies the particular PDU. A unique identifier must be allocated to each PDU in the
     * range 0x00000000 to 0xooooo1FF
     * A unique command identifier must be added to each response PDU in the rage 0x80000000 to 0x800001FF
     */
    private Integer commandId = Constants.DEFAULT_COMMAND_ID;
    /**
     * The command_status field indicates the success or failure of an SMPP request.
     */
    private Integer commandStatus = Constants.STATUS_OK;

    private Integer sequenceNumber = 1;

    public Header() {
    }

    public ByteBuffer getData() {
        ByteBuffer buffer = new ByteBuffer();
        buffer.appendInt(getCommandLength());
        buffer.appendInt(getCommandId());
        buffer.appendInt(getCommandStatus());
        buffer.appendInt(getSequenceNumber());
        return buffer;
    }

    public void setData(ByteBuffer buffer) throws NotEnoughDataInByteBufferException,
            TerminatingZeroNotFoundException,
            UnsupportedEncodingException {
        setCommandLength(buffer.removeInt());
        setCommandId(buffer.removeInt());
        setCommandStatus(buffer.removeInt());
        setSequenceNumber(buffer.removeInt());
    }

    public Integer getCommandLength() {
        return commandLength;
    }

    public Integer getCommandId() {
        return commandId;
    }

    public Integer getCommandStatus() {
        return commandStatus;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setCommandLength(Integer commandLength) {
        this.commandLength = commandLength;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public void setCommandStatus(Integer cmdStatus) {
        this.commandStatus = cmdStatus;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
