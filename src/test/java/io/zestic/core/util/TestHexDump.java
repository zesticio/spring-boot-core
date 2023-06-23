package io.zestic.core.util;

public class TestHexDump {

    /**
     * The constructor
     */
    public TestHexDump() {
        super();
        String song = "Mary had a little lamb, Its fleece was white as snow, And every where that Mary went The lamb was sure to go; He followed her to school one day, That was against the rule, It made the children laugh and play, To see a lamb at school.";

        dumpBuffer(song.getBytes(), 32);
    }

    public void dumpBuffer(byte[] data, int widthSize) {
        int endPt = widthSize;
        int len = data.length;
        byte[] tempBuffer = new byte[widthSize];

        if ((widthSize % 16) != 0) {
            System.err.println("Error: widthSize value [" + widthSize + "] is not a multiple of 16.");
        } else {
            if (widthSize == 16) {
                System.out.println("Address  0 1 2 3  4 5 6 7  8 9 A B  C D E F  0123456789ABCDEF");
                System.out.println("------- -------- -------- -------- --------  ----------------");
            } else if (widthSize == 32) {
                System.out.println("Address  0 1 2 3  4 5 6 7  8 9 A B  C D E F  0 1 2 3  4 5 6 7  8 9 A B  C D E F  0123456789ABCDEF0123456789ABCDEF");
                System.out.println("------- -------- -------- -------- -------- -------- -------- -------- --------  --------------------------------");
            }

            for (int i = 0; i < len; i += widthSize) {
                if (i + widthSize >= len)
                    endPt = len - i;

                for (int j = 0; j < endPt; j++)
                    tempBuffer[j] = data[i + j];

                System.out.println(formatHex(tempBuffer, (i + widthSize < len ? widthSize : len - i), i, widthSize));
            }
        }
    }

    /**
     * Format an array of bytes into a hex display
     */
    private String formatHex(byte[] src, int lenSrc, int index, int width) {
        int i, j;
        int g = width / 4; /* number of groups of 4 bytes */
        int d = g * 9;     /* hex display width */
        StringBuffer sb = new StringBuffer();

        if ((src == null) ||
                (lenSrc < 1) || (lenSrc > width) ||
                (index < 0) ||
                (g % 4 != 0) ||   /* only allow width of 16 / 32 / 48 etc. */
                (d < 36)) {
            return "";
        }

        String hdr = Integer.toHexString(index).toUpperCase();
        if (hdr.length() <= 6)
            sb.append("000000".substring(0, 6 - hdr.length()) + hdr + ": ");
        else
            sb.append(hdr + ": ");

        /* hex display 4 by 4 */
        for (i = 0; i < lenSrc; i++) {
            sb.append("" + "0123456789ABCDEF".charAt((src[i]) >> 4) + "0123456789ABCDEF".charAt((src[i] & 0x0F)));

            if (((i + 1) % 4) == 0)
                sb.append(" ");
        }

        /* blank fill hex area if we do not have "width" bytes */
        if (lenSrc < width) {
            j = d - (lenSrc * 2) - (lenSrc / 4);
            for (i = 0; i < j; i++)
                sb.append(" ");
        }

        /* character display */
        sb.append(" ");
        for (i = 0; i < lenSrc; i++) {
            if (Character.isISOControl((char) src[i]))
                sb.append(".");
            else
                sb.append((char) src[i]);
        }

        /* blank fill character area if we do not have "width" bytes */
        if (lenSrc < width) {
            j = width - lenSrc;
            for (i = 0; i < j; i++)
                sb.append(" ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        new TestHexDump();
    }
}
